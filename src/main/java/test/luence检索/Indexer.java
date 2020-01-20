package test.luence检索;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/8/8
 * @描述 建立分词索引
 */
public class Indexer {
    /**
     * 写索引实例
     */
    private IndexWriter writer;

    /**
     * 构造方法，实例化IndexWriter
     * @param indexDir
     * @throws Exception
     */
    public Indexer(String indexDir) throws Exception{
        Directory dir = FSDirectory.open(Paths.get(indexDir));
        //标准分词器，会自动去掉空格，is a the等单词
        Analyzer analyzer = new StandardAnalyzer();
        //将标准分词器配写到索引的配置中
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        //实例化写索引对象
        writer = new IndexWriter(dir,config);

    }

    /**
     * 索引指定目录下的所有文件
     * @param dataDir
     * @return
     * @throws Exception
     */
    public int indexAll(String dataDir) throws Exception{
        //获取该路径下的所有文件
        File[] files = new File(dataDir).listFiles();
        if (null != files){
            for (File file:files){
                //调用下面的 indexFile方法，对每个文件进行索引
                indexFile(file);
            }
        }
        //返回索引的文件数
        return  writer.numRamDocs();
    }

    /**
     * 索引指定的文件
     * @param file
     * @throws Exception
     */
    private void indexFile(File file) throws Exception{
        System.out.println("索引文件的路径："+file.getCanonicalPath());
        //调用下面的getDocument 方法，获取文件的 Document
        Document doc = getDocument(file);
        //将doc添加到索引中
        writer.addDocument(doc);
    }

    /**
     * 获取文档，文档里再设置每个字段，就类似于数据库的一行记录
     * @param file
     * @return
     * @throws Exception
     */
    private Document getDocument(File file) throws  Exception{
        Document doc = new Document();
        //开始添加字段
        //添加内容
        doc.add(new TextField("contents",new FileReader(file)));
        //添加文件名，并把这个字段存到索引文件库
        doc.add(new TextField("fileName",file.getName(), Field.Store.YES));
        //添加文件路径
        doc.add(new TextField("fullPath",file.getCanonicalPath(),Field.Store.YES));
        return doc;
    }
    //检索
    public static void search(String indexDir,String q) throws Exception{

        //获取要查询的路径，也就是索引所在的位置
        Directory dir = FSDirectory.open(Paths.get(indexDir));
        IndexReader reader = DirectoryReader.open(dir);
        //构建 IndexSearcher
        IndexSearcher searcher = new IndexSearcher(reader);
        //标准分词器，会自动去掉空格，is a the 单词
        Analyzer analyzer = new StandardAnalyzer();
        //查询解析器
        QueryParser parser = new QueryParser("contents",analyzer);
        //通过解析要查询的 String,获取查询对象，q 为传进来的待查的字符串
        Query query = parser.parse(q);

        //记录索引开始时间
        long startTime = System.currentTimeMillis();
        //开始查询，查询前10条数据，将记录保存在 docs 中
        TopDocs docs = searcher.search(query, 10);
        //记录索引结束时间
        long endTime = System.currentTimeMillis();
        System.out.println("匹配"+q+"共耗时"+(endTime-startTime)+"毫秒");
        System.out.println("查询到"+docs.totalHits+"条记录");

        //取出每条查询结果
        for (ScoreDoc scoreDoc : docs.scoreDocs) {
            //scoreDoc.doc相当于docID,根据这个id获取文档
            Document doc = searcher.doc(scoreDoc.doc);
            //fullPath 是刚刚建立索引的时候我们定义的一个字段，表示路径，也可以取其他的内容
            System.out.println(doc.get("fullPath"));
        }
        reader.close();
    }
   //创建索引测试
    /*public static void main(String[] args) {
        //索引保存到的路径
        String indexDir = "E:\\lucene";
        //需要索引的文件数据存方的目录
        String dataDir = "E:\\lucene\\data";
        Indexer indexer = null;
        int indexNum = 0;
        //记录索引开始时间
        long startTime = System.currentTimeMillis();
        try {
            //开始构建索引
            indexer = new Indexer(indexDir);
            indexNum = indexer.indexAll(dataDir);
        }catch (Exception e){
            e.printStackTrace();
        }
        //记录索引结束时间
        long endTime = System.currentTimeMillis();
        System.out.println("索引耗时"+(endTime-startTime)+"毫秒");
        System.out.println("共索引了"+indexNum+"个文件");
    }*/
    //搜索测试 报错
  /* public static void main(String[] args) {
       String indexDir = "E:\\lucene";
       //查询的字符串
       String q = "security";
       try {
           search(indexDir,q);
       }catch (Exception e){
           e.printStackTrace();
       }
   }*/




}
