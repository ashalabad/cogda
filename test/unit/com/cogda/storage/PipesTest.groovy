package com.cogda.storage

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin

import java.util.zip.ZipInputStream

/**
 * Created with IntelliJ IDEA.
 * User: igor_pol
 * Date: 7/30/13
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 *
 */
@TestMixin(GrailsUnitTestMixin)
class PipesTest {
    private def text="A quick brown fox jumped over a lazy dog back. And ran away as fast as it could. what a smart fox"

    public void testReadZippedStreamSmallBuffer() throws IOException
    {
        ByteArrayInputStream bs=new ByteArrayInputStream(text.getBytes())
        ByteArrayOutputStream bo=new ByteArrayOutputStream()
        def pipe=new ZippingPipe(bs)
        byte[] buffer=new byte[16]
        int count;
        while((count=pipe.read(buffer))!=-1){
             bo.write(buffer,0,count)
        }
        byte[] data=bo.toByteArray();
        String header=new String(data,0,2)
        assert "PK"==header
        def unzipped=unzip(data)
        assert text==unzipped
    }
    public void testReadZippedStreamLargeBuffer() throws IOException
    {
        ByteArrayInputStream bs=new ByteArrayInputStream(text.getBytes())
        ByteArrayOutputStream bo=new ByteArrayOutputStream()
        def pipe=new ZippingPipe(bs)
        byte[] buffer=new byte[8192];
        int count;
        while((count=pipe.read(buffer))!=-1){
            bo.write(buffer,0,count)
        }
        byte[] data=bo.toByteArray();
        String header=new String(data,0,2)
        assert "PK"==header
        assert  data.length<buffer.length
        def unzipped=unzip(data)
        assert text==unzipped
    }
    public void testReadZippedStreamByBytesBuffer() throws IOException
    {
        ByteArrayInputStream bs=new ByteArrayInputStream(text.getBytes())
        ByteArrayOutputStream bo=new ByteArrayOutputStream()
        def pipe=new ZippingPipe(bs)
        int count;
        while((count=pipe.read())!=-1){
            bo.write(count)
        }
        byte[] data=bo.toByteArray();
        String header=new String(data,0,2)
        assert "PK"==header
        def unzipped=unzip(data)
        assert text==unzipped
    }

    public void testReadZippedStreamLargeString() throws IOException
    {
        StringBuffer sb=new StringBuffer()
        for(i in 1..1024) {
            sb.append(text)
        }
        byte[] btxt=sb.toString().getBytes()
        ByteArrayInputStream bs=new ByteArrayInputStream(btxt)
        ByteArrayOutputStream bo=new ByteArrayOutputStream()
        def pipe=new ZippingPipe(bs)
        byte[] buffer=new byte[8192];
        int count;
        while((count=pipe.read(buffer))!=-1){
            bo.write(buffer,0,count)
        }
        byte[] data=bo.toByteArray();
        String header=new String(data,0,2)
        assert "PK"==header
        assert  data.length<btxt.length
        def unzipped=unzip(data)
        assert sb.toString()==unzipped

    }

    private String unzip(byte[] data){
        ByteArrayInputStream bi=new ByteArrayInputStream(data)
        ByteArrayOutputStream bo=new ByteArrayOutputStream()
        byte[] buffer=new byte[8192];
        ZipInputStream zin=new ZipInputStream(bi)
        zin.getNextEntry()
        int count;
        while((count=zin.read(buffer))!=-1)
            bo.write(buffer,0,count)
        return new String(bo.toByteArray())
    }
}
