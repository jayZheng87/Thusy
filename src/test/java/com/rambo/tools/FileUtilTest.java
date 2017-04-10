package test.com.rambo.tools; 

import com.rambo.tools.FileUtil;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* FileUtil Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 10, 2017</pre> 
* @version 1.0 
*/ 
public class FileUtilTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: readFileContent(File file, String encoding) 
* 
*/ 
@Test
public void testReadFileContent() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: readStreamContent(InputStream stream, String encoding) 
* 
*/ 
@Test
public void testReadStreamContent() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: normalizeAbsolutePath(String path) 
* 
*/ 
@Test
public void testNormalizeAbsolutePathPath() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: normalizeAbsolutePath(String path, boolean removeTrailingSlash) 
* 
*/ 
@Test
public void testNormalizeAbsolutePathForPathRemoveTrailingSlash() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: normalizeRelativePath(String path) 
* 
*/ 
@Test
public void testNormalizeRelativePathPath() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: normalizeRelativePath(String path, boolean removeTrailingSlash) 
* 
*/ 
@Test
public void testNormalizeRelativePathForPathRemoveTrailingSlash() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: normalizePath(String path) 
* 
*/ 
@Test
public void testNormalizePathPath() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: normalizePath(String path, boolean removeTrailingSlash) 
* 
*/ 
@Test
public void testNormalizePathForPathRemoveTrailingSlash() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getAbsolutePathBasedOn(String basedir, String path) 
* 
*/ 
@Test
public void testGetAbsolutePathBasedOn() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getSystemDependentAbsolutePathBasedOn(String basedir, String path) 
* 
*/ 
@Test
public void testGetSystemDependentAbsolutePathBasedOn() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getRelativePath(String basedir, String path) 
* 
*/ 
@Test
public void testGetRelativePath() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getExtension(String fileName) 
* 
*/ 
@Test
public void testGetExtensionFileName() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getExtension(String fileName, boolean toLowerCase) 
* 
*/ 
@Test
public void testGetExtensionForFileNameToLowerCase() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getExtension(String fileName, String nullExt) 
* 
*/ 
@Test
public void testGetExtensionForFileNameNullExt() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getExtension(String fileName, String nullExt, boolean toLowerCase) 
* 
*/ 
@Test
public void testGetExtensionForFileNameNullExtToLowerCase() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getFileNameAndExtension(String path) 
* 
*/ 
@Test
public void testGetFileNameAndExtensionPath() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getFileNameAndExtension(String path, boolean extensionToLowerCase) 
* 
*/ 
@Test
public void testGetFileNameAndExtensionForPathExtensionToLowerCase() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: normalizeExtension(String ext) 
* 
*/ 
@Test
public void testNormalizeExtension() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: resolve(String url, String relativePath) 
* 
*/ 
@Test
public void testResolve() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: deletefile(String delpath) 
* 
*/ 
@Test
public void testDeletefile() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: delete(File file) 
* 
*/ 
@Test
public void testDelete() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: createIncDir(String baseDir1) 
* 
*/ 
@Test
public void testCreateIncDir() throws Exception { 
//TODO: Test goes here...
    FileUtil.createIncDir("D:\\Workspaces\\OnWork\\nxdzswj\\WebContent","F:\\Garbage\\0. 发布的增量");
} 

/** 
* 
* Method: main(String[] args) 
* 
*/ 
@Test
public void testMain() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getFileName() 
* 
*/ 
@Test
public void testGetFileName() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getExtension() 
* 
*/ 
@Test
public void testGetExtension() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: toString() 
* 
*/ 
@Test
public void testToString() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: normalizePath(String path, boolean forceAbsolute, boolean forceRelative, boolean removeTrailingSlash) 
* 
*/ 
@Test
public void testNormalizePath() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = FileUtil.getClass().getMethod("normalizePath", String.class, boolean.class, boolean.class, boolean.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: indexOfSlash(char[] chars, int beginIndex, boolean slash) 
* 
*/ 
@Test
public void testIndexOfSlash() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = FileUtil.getClass().getMethod("indexOfSlash", char[].class, int.class, boolean.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
