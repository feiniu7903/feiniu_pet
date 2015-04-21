package com.lvmama.push.codec;
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
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * 
 * @author lvmama dengcheng
 *
 */
public class PushDecoderBack extends CumulativeProtocolDecoder {

    //private final Log log = LogFactory.getLog(CumulativeProtocolDecoder.class);
	
	//存放断包数据
	private final String BF_STR=  "BF_STR";
	
	private final Charset charset; 

    /**
     * 构造依赖
     * @param charset
     */
    public PushDecoderBack(Charset charset) { 
        this.charset = charset; 
 
    } 
    
	 @Override
	    protected boolean doDecode(IoSession session, IoBuffer in,
	            ProtocolDecoderOutput out) throws Exception {
	        CharsetDecoder decoder = charset.newDecoder();  
	        //设置数据存放的IoBuffer大小   
	       // IoBuffer b =(IoBuffer) session.getAttribute("BUFFER");
	        IoBuffer ioBuffer = null;
	        if(session.getAttribute(BF_STR)!=null){ //处理断包的情况
       	  //拼包。
	        	ioBuffer = (IoBuffer) session.getAttribute(BF_STR);

         } else {
	           ioBuffer = IoBuffer.allocate(1024).setAutoExpand(true);
         }
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
		              String message = new String (bt,decoder.charset());
		                 //重置ioBuffer
		              ioBuffer = IoBuffer.allocate(100).setAutoExpand(true);
		              session.setAttribute(BF_STR,null);
		              ioBuffer.clear();
		              out.write(message);
		              return true;
				default:
					ioBuffer.put(bte);

					break;
				}
	         }
	        //将字符串 保持到 session 共下次包到来后 组装用
	        session.setAttribute(BF_STR,ioBuffer);
	        return true;
		 
	
	    }
	

}
