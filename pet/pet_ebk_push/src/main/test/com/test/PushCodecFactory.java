package com.test;
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

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/** 
 * Factory class that specifies the encode and decoder to use for parsing XMPP stanzas.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class PushCodecFactory implements ProtocolCodecFactory {

    private final PushEncoder encoder;

    private final PushDecoder decoder;

    public PushCodecFactory() { 
        this(Charset.defaultCharset()); 
    } 
 
    public PushCodecFactory(Charset charSet) { 
        this.encoder = new PushEncoder(charSet); 
        this.decoder = new PushDecoder(charSet); 
    } 
 
    @Override 
    public ProtocolDecoder getDecoder(IoSession session) throws Exception { 
        return decoder; 
    } 
 
    @Override 
    public ProtocolEncoder getEncoder(IoSession session) throws Exception { 
        return encoder; 
    } 

}
