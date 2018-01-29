package edu.shapovalov.library.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 10.01.2018
 */

@Controller
public class DownloadController {
    private String pathToFile;

    public DownloadController(@Value("${book.upload.path}") String pathToFile) {
        this.pathToFile = pathToFile;
    }

    @RequestMapping(value = "/download/{book_id}", method = RequestMethod.GET)
    public void download(@PathVariable Integer book_id, HttpServletResponse response) throws IOException {

        File file = new File(pathToFile + "/" + Integer.toString(book_id));
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        String mimeType = URLConnection.guessContentTypeFromStream(inputStream);

        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setContentLength((int) file.length());

        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }
}
