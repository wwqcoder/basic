package test.工具合集.序列号;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/8/9
 * @描述
 */

import test.工具合集.logger.Formatter;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Hashtable;

/**
 * 根据文件取序列号的实现
 *
 * @author taige.wuhq
 *
 */
public class SerialUtil {
    private static final String DEFAULT_APP = ".serial";
    private static final Hashtable<String, MappedByteBuffer> serials = new Hashtable<String, MappedByteBuffer>();
    private static final Hashtable<String, FileChannel> fchannels = new Hashtable<String, FileChannel>();
    private static final Object obj = new Object();

    public static String next8() {
        return next(8);
    }

    /**
     * 获取下一个指定长度的String型的序列号
     *
     * @param len 序列号的长度
     * @return 指定长度的下一个序列号(左补0)
     */
    public static String next(int len) {
        return next(len, "");
    }

    public static String next(int len, String app) {
        if (len > 19) {
            len = 19;
        }
        long n = next(app);
        return Formatter.ralign(String.valueOf(n), len);
    }

    /**
     * 获取下一个序列号
     *
     * @return 下一个序列号, 如果出现错误, 则返回0
     */
    public static long next() {
        return next("");
    }

    public static long next(String app) {
        String appKey = app + DEFAULT_APP;
        synchronized (obj) {
            FileChannel fc = (FileChannel) fchannels.get(appKey);
            MappedByteBuffer serial = (MappedByteBuffer) serials.get(appKey);
            try {
                if (serial == null) {
                    //获得一个可读写的随机存取文件对象
                    RandomAccessFile RAFile = new RandomAccessFile(appKey, "rw");
                    if (RAFile.length() < 8) {
                        RAFile.writeLong(0);
                    }

                    //获得相应的文件通道
                    fc = RAFile.getChannel();

                    //取得文件的实际大小，以便映像到共享内存
                    int size = (int) fc.size();

                    //获得共享内存缓冲区，该共享内存可读写
                    serial = fc.map(FileChannel.MapMode.READ_WRITE, 0, size);

                    fchannels.put(appKey, fc);
                    serials.put(appKey, serial);
                }

                FileLock flock = fc.lock();
                serial.rewind();
                long serno = serial.getLong();
                serno++;
                serial.flip();
                serial.putLong(serno);
                serial.force(); //add on 2007.12.05
                flock.release();

                return serno;
            } catch (IOException e) {
                e.printStackTrace();
                return 0L;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        long l = Long.parseLong(args[0]);
        String appKey;
        if (args.length > 1) {
            appKey = args[1] + DEFAULT_APP;
        } else {
            appKey = DEFAULT_APP;
        }
        RandomAccessFile RAFile = new RandomAccessFile(appKey, "rw");
        RAFile.writeLong(l);
        RAFile.close();
    }

    public long nextSeq() {
        return next();
    }
}
