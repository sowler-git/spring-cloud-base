package com.itmy.picture.utils;

import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.emoji.EmojiImageType;
import com.vladsch.flexmark.ext.emoji.EmojiShortcutType;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * 文件操作工具类
 *
 */
@Slf4j
public class FileUtils {

    // 图片类型
    private static List<String> pictureTypes = new ArrayList<>();

    static {
        pictureTypes.add("jpg");
        pictureTypes.add("jpeg");
        pictureTypes.add("bmp");
        pictureTypes.add("gif");
        pictureTypes.add("png");
    }

    /**
     * 判断是否是图片
     *
     * @param fileName
     * @return
     */
    public static Boolean isPicture(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return false;
        }
        String expandName = getPicExpandedName(fileName);
        return pictureTypes.contains(expandName);
    }

    /**
     * 判断是否为markdown文件
     *
     * @param fileName
     * @return
     */
    public static Boolean isMarkdown(String fileName) {
        return fileName.indexOf(".md") > -1;
    }

    /**
     * Markdown转Html
     *
     * @param markdown
     * @return
     */
    public static String markdownToHtml(String markdown) {
        MutableDataSet options = new MutableDataSet().set(Parser.EXTENSIONS, Arrays.asList(
                AutolinkExtension.create(),
                EmojiExtension.create(),
                StrikethroughExtension.create(),
                TaskListExtension.create(),
                TablesExtension.create()
        ))
                // set GitHub table parsing options
                .set(TablesExtension.WITH_CAPTION, false)
                .set(TablesExtension.COLUMN_SPANS, false)
                .set(TablesExtension.MIN_HEADER_ROWS, 1)
                .set(TablesExtension.MAX_HEADER_ROWS, 1)
                .set(TablesExtension.APPEND_MISSING_COLUMNS, true)
                .set(TablesExtension.DISCARD_EXTRA_COLUMNS, true)
                .set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, true)

                // setup emoji shortcut options
                // uncomment and change to your image directory for emoji images if you have it setup
                //.set(EmojiExtension.ROOT_IMAGE_PATH, emojiInstallDirectory())
                .set(EmojiExtension.USE_SHORTCUT_TYPE, EmojiShortcutType.GITHUB)
                .set(EmojiExtension.USE_IMAGE_TYPE, EmojiImageType.IMAGE_ONLY)
                // other options
                ;
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Node document = parser.parse(markdown);
        String html = renderer.render(document);
        return html;
    }

    /**
     * Html 转 Markdown
     *
     * @param html
     * @return
     */
    public static String htmlToMarkdown(String html) {
        MutableDataSet options = new MutableDataSet();
        String markdown = FlexmarkHtmlConverter.builder(options).build().convert(html);
        return markdown;
    }

    /**
     * 上传文件
     *
     * @param pathRoot 物理路径webapp所在路径
     * @return 返回图片上传的地址
     * @throws IOException
     * @throws IllegalStateException
     */
    public static String uploadFile(String pathRoot, String baseUrl, MultipartFile avatar) throws IllegalStateException, IOException {
        String path = "";
        if (!avatar.isEmpty()) {
            //获得文件类型（可以判断如果不是图片，禁止上传）  
            String contentType = avatar.getContentType();
            if (contentType.indexOf("image") != -1) {
                //获得文件后缀名称
                String imageName = contentType.substring(contentType.indexOf("/") + 1);
                path = baseUrl + UUID.randomUUID().toString() + "." + imageName;
                avatar.transferTo(new File(pathRoot + path));
            }
        }
        return path;
    }


    /**
     * 获取后缀名
     *
     * @param fileName
     * @return
     */
    public static String getPicExpandedName(String fileName) {
        String ext = "";
        if (StringUtils.isNotBlank(fileName) &&
                StringUtils.contains(fileName, ".")) {
            ext = StringUtils.substring(fileName, fileName.lastIndexOf(".") + 1);
        }
        ext = ext.toLowerCase();
        if (ext == null || ext.length() < 1) {
            ext = "jpg";
        }

        return ext;
    }

    /**
     * 根据OriginalFilename获取FileName【去除文件后缀】
     * @param originalFilename
     * @return
     */
    public static String getFileName(String originalFilename) {
        String fileName = "";
        if (StringUtils.isNotBlank(originalFilename) &&
                StringUtils.contains(originalFilename, ".")) {
            fileName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        }
        return fileName;
    }

    /**
     * 从Request中获取文件
     *
     * @return
     */
    public static List<MultipartFile> getMultipartFileList(HttpServletRequest request) {
        List<MultipartFile> files = new ArrayList<>();
        try {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            if (request instanceof MultipartHttpServletRequest) {
                // 将request变成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Iterator<String> iter = multiRequest.getFileNames();
                // 检查form中是否有enctype="multipart/form-data"
                if (multipartResolver.isMultipart(request) && iter.hasNext()) {
                    // 获取multiRequest 中所有的文件名
                    while (iter.hasNext()) {
                        List<MultipartFile> fileRows = multiRequest
                                .getFiles(iter.next().toString());
                        if (fileRows != null && fileRows.size() != 0) {
                            for (MultipartFile file : fileRows) {
                                if (file != null && !file.isEmpty()) {
                                    files.add(file);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            log.error("解析MultipartRequest错误", ex);
        }
        return files;
    }

    public static final String[] IMG_FILE = {"bmp", "jpg", "png", "tif", "gif", "jpeg", "webp"};
    public static final String[] DOC_FILE = {"doc", "docx", "txt", "hlp", "wps", "rtf", "html", "pdf", "md", "sql", "css", "js", "vue", "java"};
    public static final String[] VIDEO_FILE = {"avi", "mp4", "mpg", "mov", "swf"};
    public static final String[] MUSIC_FILE = {"wav", "aif", "au", "mp3", "ram", "wma", "mmf", "amr", "aac", "flac"};
    public static final String[] ALL_FILE = {"bmp", "jpg", "png", "tif", "gif", "jpeg", "webp",
            "doc", "docx", "txt", "hlp", "wps", "rtf", "html", "pdf", "md", "sql", "css", "js", "vue", "java",
            "avi", "mp4", "mpg", "mov", "swf",
            "wav", "aif", "au", "mp3", "ram", "wma", "mmf", "amr", "aac", "flac"
    };
    public static final int IMAGE_TYPE = 1;
    public static final int DOC_TYPE = 2;
    public static final int VIDEO_TYPE = 3;
    public static final int MUSIC_TYPE = 4;
    public static final int OTHER_TYPE = 5;

    public static List<String> getFileExtendsByType(int fileType) {

        List<String> fileExtends;
        switch (fileType) {
            case IMAGE_TYPE:
                fileExtends = Arrays.asList(IMG_FILE);
                break;

            case DOC_TYPE:
                fileExtends = Arrays.asList(DOC_FILE);
                break;
            case VIDEO_TYPE:
                fileExtends = Arrays.asList(VIDEO_FILE);
                break;
            case MUSIC_TYPE:
                fileExtends = Arrays.asList(MUSIC_FILE);
                break;
            case OTHER_TYPE: {
                fileExtends = Arrays.asList(ALL_FILE);
            }
            break;
            default:
                fileExtends = new ArrayList<>();
                break;


        }
        return fileExtends;
    }

    /**
     * 判断是否为图片文件
     *
     * @param extendName 文件扩展名
     * @return 是否为图片文件
     */
    public static boolean isImageFile(String extendName) {
        for (int i = 0; i < IMG_FILE.length; i++) {
            if (extendName.equalsIgnoreCase(IMG_FILE[i])) {
                return true;
            }
        }
        return false;
    }


    public static String pathSplitFormat(String filePath) {
        String path = filePath.replace("///", "/")
                .replace("//", "/")
                .replace("\\\\\\", "\\")
                .replace("\\\\", "\\");
        return path;
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件名
     * @return 文件扩展名
     */
    public static String getFileType(String fileName) {
        if (fileName.lastIndexOf(".") == -1) {
            return "";
            //这里暂时用jpg，后续应该去获取真实的文件类型
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 获取不包含扩展名的文件名
     *
     * @param fileName 文件名
     * @return 文件名（不带扩展名）
     */
    public static String getFileNameNotExtend(String fileName) {
        String fileType = getFileType(fileName);
        return fileName.replace("." + fileType, "");
    }


    public static void main(String[] args) {
        System.out.println(pictureTypes.contains("png"));
    }
}
