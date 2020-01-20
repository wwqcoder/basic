package test.luence检索;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 zhaojingen
 * @创建时间  2019/8/8
 * @描述 建立中文索引
 */
public class ChineseIndexer {
    /**
     * 存放索引的位置
     */
    private Directory dir;

    //准备一下用来测试的数据
    //用来标识文档
    private Integer ids[] = {1,2,3};
    private String citys[] = {"上海","南京","青岛"};
    private String descs[] = {"上海是一个繁华的城市","南京是一个文化的城市","青岛是一个美丽的城市"};

    /**
     * 生成索引
     * @param indexDir
     * @throws Exception
     */
    public void index(String indexDir) throws Exception{
        dir = FSDirectory.open(Paths.get(indexDir));
        IndexWriter writer = getWriter();
        for (int i = 0; i <ids.length ; i++) {
            Document doc = new Document();
            doc.add(new StringField("id",ids[i].toString(), Field.Store.YES));
            doc.add(new StringField("city",citys[i], Field.Store.YES));
            doc.add(new TextField("desc",descs[i], Field.Store.YES));
            writer.addDocument(doc);
        }
        writer.close();
    }

    /**
     * 获取 IndexWriter 实例
     * @return
     * @throws Exception
     */
    private IndexWriter getWriter() throws Exception{
        //使用中文分词器
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        //将中文分词器配到写索引的配置中
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        //实例化写索引对象
        IndexWriter writer = new IndexWriter(dir,config);
        return  writer;
    }


    public static void main(String[] args) throws Exception{
        new ChineseIndexer().index("E:\\lucene");
    }
}
