/**
 * algorithm 版权声明
 * Copyright (c) 2019, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20190524    lsf     初始版本
 */
package com.algorithm.io.download;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 多文件打包zip下载
 *
 */
@Controller
public class ZipDownload {

    private static Logger logger = LoggerFactory.getLogger( ZipDownload.class );
    /**
     * 直接网络流压缩下载，不会在原地生成zip文件
     * @param response
     * @param files
     * @throws Exception
     */
    @GetMapping("/download")
    public void downloadZip(  @RequestParam(value = "file", required = false)List<String> files,  HttpServletResponse response){
        downloadZip(files,"/opt/uuwifi/log/uuwifiws",response);
    }
    public void downloadZip(List<String> files, String filePath, HttpServletResponse response){
        try{
            ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
            BufferedOutputStream bos = new BufferedOutputStream(zos);

            for( String filename : files){
                File file =  new File(filePath + File.separator + filename);

                BufferedInputStream bis =new BufferedInputStream( new BufferedInputStream( new FileInputStream( file ), 1024 * 10 ) );
                zos.putNextEntry(new ZipEntry(filename));

                int len = 0;
                byte[] buf = new byte[10 * 1024];
                while( (len=bis.read(buf, 0, buf.length)) != -1){
                    bos.write(buf, 0, len);
                }
                bis.close();
                bos.flush();
            }
            bos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 批量文件压缩成
     * @param fileList
     * @param zipPath
     * @param zipName
     * @return
     * @throws IOException
     */
    public File zipFiles( List< File > fileList, String zipPath, String zipName ) throws IOException {
        // 如果被压缩文件中有重复，会重命名
        Map< String, String > namePathMap = getTransferName( fileList );
        File zipPathFile = new File( zipPath );
        // 文件夹不存在则创建
        if ( !zipPathFile.exists() ) {
            zipPathFile.mkdirs();
        }
        File zipFile = new File( zipPath + File.separator + zipName );
        if ( !zipFile.exists() ) {
            zipFile.createNewFile();
        }
        ZipOutputStream zos = null;
        BufferedInputStream bis = null;
        try {
        // 存放的目标文件
            zos = new ZipOutputStream( new BufferedOutputStream( new FileOutputStream( zipFile.getPath() ) ) );
            Set< String > keySet = namePathMap.keySet();
            ZipEntry zipEntry = null;
            for ( String key : keySet ) { // key是文件名，value是path
        // 指定源文件
                File sourceFile = new File( namePathMap.get( key ) );
        // 创建ZIP实体，并添加进压缩包,指定压缩文件中的文件名
                zipEntry = new ZipEntry( key );
                zos.putNextEntry( zipEntry );
        // 读取待压缩的文件并写进压缩包里
                bis = new BufferedInputStream( new BufferedInputStream( new FileInputStream( sourceFile ), 1024 * 10 ) );
                byte[] bufs = new byte[ 1024 * 10 ];
                int read = 0;
                while ( ( read = ( bis.read( bufs, 0, 1024 * 10 ) ) ) != -1 ) {
                    zos.write( bufs, 0, read );
                }
                if ( bis != null ) {
                    bis.close();
                }
            }

        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
        // 关闭流
            if ( bis != null ) {
                bis.close();
            }
            if ( null != zos ) {
                zos.close();
            }
        }
        return zipFile;
    }

    /**
     * 计算压缩包如果已存在重复的名称，则在重复文件后面跟上数字 如: 文件(1).doc,文件(2).doc
     * @param fileList
     * @return
     */
    public Map< String, String > getTransferName( List< File > fileList ) {
        if ( fileList == null || fileList.size() == 0 ) {
            return new HashMap< String, String >();
        }
        // key存放文件名，value存放path
        Map< String, String > fileNameMap = new HashMap<>();
        List< String > fileNames = new ArrayList<>();
        for ( File file : fileList ) {
            // 获取文件名
            String fileName = file.getName();
            int count = 0;

            for ( String name : fileNames ) {
                if ( name != null && name.equals( fileName ) ) {
                    count++;
                }
            }
            fileNames.add( fileName );
            if ( count > 0 ) {
                int lastIndex = fileName.lastIndexOf( '.' );
                String name = fileName.substring( 0, lastIndex );
                String type = fileName.substring( lastIndex + 1, fileName.length() );
                fileName = new StringBuilder().append( name ).append( "(" ).append( count ).append( ")" ).append( "." )
                        .append( type ).toString();
                fileNameMap.put( fileName, file.getPath() );
            } else {
                fileNameMap.put( fileName, file.getPath() );
            }
        }
        return fileNameMap;
    }


    /**
     * 将文件写出到流
     */
    private void writeOut( HttpServletResponse response, File zipFile ) throws IOException {
        response.reset();
        response.setContentType( "application/zip" );
        response.setCharacterEncoding( "utf-8" );
        response.setHeader( "Content-Disposition", "attachment;filename=" + zipFile.getName() );
        OutputStream outputStream = response.getOutputStream();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream( zipFile.getPath() );
            int len = 0;
            byte[] buffer = new byte[ 1024 ];
            while ( ( len = fis.read( buffer ) ) > 0 ) {
                outputStream.write( buffer, 0, len );
            }
            outputStream.flush();
        } finally {
            if ( null != fis ) {
                fis.close();
            }
            if ( null != outputStream ) {
                outputStream.close();
            }
        }
    }

}