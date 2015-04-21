package com.ejingtong.push.code;
/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */


import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * 
 * @author dengcheng
 *
 */
public class PushDecodeBack extends CumulativeProtocolDecoder {

    //private final Log log = LogFactory.getLog(CumulativeProtocolDecoder.class);
	
	//存放断包数据
	private final String BF_STR=  "BF_STR";
	
	private final Charset charset; 
    
    /**
     * 构造依赖
     * @param charset
     */
    public PushDecodeBack(Charset charset) { 
        this.charset = charset; 
 
    } 
    
	 @Override
	    protected boolean doDecode(IoSession session, IoBuffer in,
	            ProtocolDecoderOutput out) throws Exception {
	        //设置数据存放的IoBuffer大小   
	       // IoBuffer b =(IoBuffer) session.getAttribute("BUFFER");
	     
	        IoBuffer ioBuffer = IoBuffer.allocate(1024).setAutoExpand(true);  
	        //System.out.println(session.getAttribute(BUFFER));
	        while(in.hasRemaining()){
	             byte bte = in.get();
	             //将数据存放到IoBuffer中

	             switch (bte) {
	            
				case '\r':
					continue;
				case '\n':
					  ioBuffer.flip();
		              byte[] bt = new byte[ioBuffer.limit()];
		              ioBuffer.get(bt);
		              String message = new String (bt,"utf-8");
		                 //重置ioBuffer
		              ioBuffer = IoBuffer.allocate(100).setAutoExpand(true);
		              if(session.getAttribute(BF_STR)!=null){
		            	  //拼包。
		            	  System.out.println("######################"+session.getAttribute(BF_STR));
		            	  
		            	  message = session.getAttribute(BF_STR)+message;
		            	  
		            	  session.setAttribute(BF_STR,null);
		              }
		              System.out.println("####"+message);
		              out.write(message);
					break;
				default:
					ioBuffer.put(bte);
					//处理断包数据
					  
					break;
				}
	         }
	 
	        if(ioBuffer.position()!=0){
	        	 ioBuffer.flip();
	             byte[] bt = new byte[ioBuffer.limit()];
	             ioBuffer.get(bt);
	             String message = new String (bt,"utf-8");
	             //将字符串 保持到 session 共下次包到来后 组装用
	             System.out.println("亲断包了哦。。!!"+ message);
	             
	             if(session.getAttribute(BF_STR)!=null){
	            	 message = session.getAttribute(BF_STR)+message;
	            	 session.setAttribute(BF_STR,message);
	             } else {
	            	 session.setAttribute(BF_STR,message);
	             }
	            
	             ioBuffer = null;
	        }
	        return true;
		 
	
	    }
	

}
