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


import java.io.UnsupportedEncodingException;
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
public class PushDecoder extends CumulativeProtocolDecoder {

    //private final Log log = LogFactory.getLog(CumulativeProtocolDecoder.class);
	
	//处理数据上下文
		private final String SESSION_BUFFER ="SESSION_BUFFER";
	
	private final Charset charset; 

    /**
     * 构造依赖
     * @param charset
     */
    public PushDecoder(Charset charset) { 
        this.charset = charset; 
 
    } 
    
	 @Override
	    protected boolean doDecode(IoSession session, IoBuffer in,
	            ProtocolDecoderOutput out) throws Exception {
		 CharsetDecoder decoder = charset.newDecoder();
		 	in.setAutoShrink(true);
			 IoBuffer sessionBuffer = (IoBuffer)session.getAttribute(SESSION_BUFFER);
			 IoBuffer buffer =  IoBuffer.allocate(2024).setAutoExpand(true);
			if(sessionBuffer!=null){
				buffer.put(sessionBuffer);
				buffer.put(in);
			} else {
				buffer.put(in);
			}
			
			buffer.flip();
			buffer.mark();
			int length = buffer.getInt(); 
			int size = buffer.remaining();
			if(size==length){//等包
					 byte[] bytes = new byte[size];
					 buffer.get(bytes);
					 String message = new String (bytes,decoder.charset());
					 out.write(message);
			} else if(size>length){//粘包
					 this.getBuffer(length,buffer,out,session);
			} else if(length>size){//断包
				 this.getBuffer(length,buffer,out,session);
			}
			return !in.hasRemaining();
	    }
	
	 public void getBuffer(int length,IoBuffer in,ProtocolDecoderOutput out,IoSession session){
		 if(in.remaining()>length){
			 byte[] bytes = new byte[length];
			 in.get(bytes);
			 String message="";
			try {
				message = new String (bytes,"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 out.write(message);
			 session.setAttribute(SESSION_BUFFER, null);
			 if(in.hasRemaining()){
				 in.mark();
				 int nlength = in.getInt();
				 this.getBuffer(nlength,in, out, session);
			 } 
		 } else {
			 /**
			  * 重置position
			  */
			 if(in.markValue()!=-1){
				 in.reset();
				 //in.flip();
			 }
			 IoBuffer buffer =  IoBuffer.allocate(1024).setAutoExpand(true);
			 buffer.put(in);
			 buffer.flip();
			 session.setAttribute(SESSION_BUFFER, buffer);
		 }
	 }

}
