<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@page import="com.lvmama.comm.utils.ServletUtil"%>
<%@page import="com.lvmama.comm.utils.MemcachedUtil"%>
<%@page import="com.lvmama.comm.utils.MemcachedSeckillUtil"%>
<%@page import="com.lvmama.front.web.seckill.SeckillMemcachedUtil"%>
<%@ page import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*,com.lvmama.comm.vo.Constant" %>
<%@ page import="java.io.OutputStream" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
	Color getRandColor(int fc,int bc){
		Random random = new Random();
		if(fc>255) fc=255;
		if(bc>255) bc=255;
		int r=fc+random.nextInt(bc-fc);
		int g=fc+random.nextInt(bc-fc);
		int b=fc+random.nextInt(bc-fc);
		return new Color(r,g,b);
	}
%>
<%
	try {
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", 0);
		int width=130, height=30;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		OutputStream os=response.getOutputStream();
		Graphics g = image.getGraphics();
		Random random = new Random();
		g.fillRect(0, 0, width, height);

		g.setFont(new Font("Times New Roman",Font.PLAIN,18));
		g.setColor(Color.WHITE);
		
		for(int i=0;i<(random.nextInt(8)+5);i++){  
	        g.setColor(new Color(238,51,136));  
	        g.drawLine(random.nextInt(100),random.nextInt(30),random.nextInt(100),random.nextInt(30));  
	    }  
		String base = SeckillMemcachedUtil.getSeckillMemcachedUtil().getValidateCode();
		//设置随机 备选的字体类型
	    String[] fontTypes = {"宋体","新宋体","黑体","楷体","隶书"};
	    String sRand = "" ;
	    for ( int i = 0; i < 4; i++) {
	       String rand = base.substring(i, i + 1);
	       sRand += rand;
	       // 设置图片上字体的颜色
	       g.setColor(new Color(238,51,136));
	       // 设置字体格式
	       g.setFont(new Font(fontTypes[random.nextInt(fontTypes.length)],Font.BOLD,18 + random.nextInt(6)));
	       // 将此汉字画到验证图片上面
	       g.drawString(rand, 24*i + 10 + random.nextInt(8), 24);
	    }
		
	    ServletUtil servlet = new ServletUtil();
	    servlet.putSession(request, response,"chineseCode", base);
		g.dispose();
	
		ImageIO.write(image, "JPEG",os);
		os.flush();
		os.close();
		os=null;
		response.flushBuffer();
		out.clear();
		out = pageContext.pushBody();
	}
	catch(IllegalStateException e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
	}
%>
