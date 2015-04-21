<%@ page import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*,com.lvmama.comm.vo.Constant" %>
<%@ page import="java.io.OutputStream" %>
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
		int width=60, height=20;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		OutputStream os=response.getOutputStream();
		Graphics g = image.getGraphics();
		Random random = new Random();
		//g.setColor(getRandColor(200,250));
		g.fillRect(0, 0, width, height);

		g.setFont(new Font("Times New Roman",Font.PLAIN,18));
		g.setColor(Color.WHITE);
		
		for(int i=0;i<(random.nextInt(8)+5);i++){  
	        g.setColor(new Color(238,51,136));  
	        g.drawLine(random.nextInt(100),random.nextInt(30),random.nextInt(100),random.nextInt(30));  
	    }  
		
		String sRand="";
		char[] ch = "ABCDEFGHIJKLMNPQRSTUVWXYZ23456789".toCharArray(); 
		int index,len=ch.length;
		for (int i=0;i<4;i++){
			index = random.nextInt(ch.length);
			String rand=String.valueOf(ch[index]);
			sRand+=rand;
			g.setColor(new Color(238,51,136));
			g.drawString(rand,13*i+6,16);
		}
		session.setAttribute(Constant.PAGE_USER_VALIDATE,sRand);
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
