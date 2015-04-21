package com.lvmama.comm.utils;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.lvmama.comm.pet.po.search.Shantou;

public class DomUtils{

    /**
     * DOM4J写XML
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) {
    	createXml("utf-8", "d:\\student.xml");
    }
    public static void createXml(String encoding,String filePath){

        try {
            XMLWriter writer = null;// 声明写XML的对象
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding(encoding);// 设置XML文件的编码格式
             File file = new File(filePath);
             file.delete();
             Document _document = DocumentHelper.createDocument();
             Element root = _document.addElement("urlset");
             Element url = root.addElement("url");
                
                for(int i=0;i<99;i++){
                	//必填
                	 url.addElement("loc").setText("http://www.xxx.com/a23125.html");
                     Element data=url.addElement("data");
                     data.addElement("name").setText("Canon 佳能 IXUS 115 HS数码相机");
                     data.addElement("outerID").setText("Canon 佳能 IXUS 115 HS数码相机");
                     data.addElement("sellerName").setText("Canon 佳能 IXUS 115 HS数码相机");
                     data.addElement("price").setText("Canon 佳能 IXUS 115 HS数码相机");
                     data.addElement("title").setText("Canon 佳能 IXUS 115 HS数码相机");
                     //非必填
                     data.addElement("category").setText("Canon 佳能 IXUS 115 HS数码相机");
                     data.addElement("subCategory").setText("Canon 佳能 IXUS 115 HS数码相机");
                     data.addElement("thirdCategory").setText("Canon 佳能 IXUS 115 HS数码相机");
                     data.addElement("targetUrl").setText("<![CDATA[http://www.amazon.cn/gp/product/B004OR2FK8/ref=s9_ri_gw_g23_ir05?pf_rd_m=A1AJ19PSB66TGU&pf_rd_s=center-7]]>");
                     data.addElement("value").setText("Canon 佳能 IXUS 115 HS数码相机");//产品原价
                     data.addElement("services").setText("Canon 佳能 IXUS 115 HS数码相机");
                     data.addElement("stock").setText("Canon 佳能 IXUS 115 HS数码相机");
                     data.addElement("city").setText("Canon 佳能 IXUS 115 HS数码相机");
                     data.addElement("image").setText("image");
                     data.addElement("sellerSiteUrl").setText("sellerSiteUrl");
                     //备注（门票和线路）
                     data.addElement("productType").setText("门票");
                     data.addElement("productType").setText("门票");
                     data.addElement("productType").setText("门票");
                     data.addElement("productType").setText("门票");

                 }
               
                writer = new XMLWriter(new FileWriter(file), format);
                writer.write(_document);
                writer.close();
             System.out.println("操作结束! ");
        } catch (Exception e) {
            e.printStackTrace();
        }

    
    }
	public static void buildXMLForShantou(List<Shantou> results,
			Integer pageCount, int curPage, String basePath) {
		
	}
}