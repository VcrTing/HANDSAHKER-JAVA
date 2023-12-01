package com.qiong.handshaker.utils.tool.staticset;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class QStaticTool {
    // 工具 方法
    public String serStr(Object src) { return (src == null) ? "" : src.toString().trim(); }

    public String resourceRootPath() throws FileNotFoundException { return ResourceUtils.getURL("classpath:").getPath(); }

    public Path genResourcePath(String folder, String fileName) throws FileNotFoundException {
        return Paths.get(resourceRootPath(), serStr(folder), serStr(fileName));
    }

    // 制造 一个 专门服务于 RESOURCES 目录 下的 输出 流
    public OutputStream genResourceOutputStream(String folder, String fileName) throws IOException {
        return Files.newOutputStream( genResourcePath(folder, fileName) );
    }
}
