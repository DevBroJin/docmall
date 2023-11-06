package com.docmall.util;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnailator;

// 파일업로드 관련 필요한 메소드
public class FileUtils {

	// 날짜폴더 생성을 위한 날짜정보
	public static String getDateFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		String str = sdf.format(date);

		// File.seperator : 각 OS별로 경로구분자를 변환
		// 유닉스, 맥, 리눅스 구분자 / 예>"2023-11-02" -> "2023-11-02"
		// 윈도우즈 구분자 \ 예>"2023-11-02" -> 예>"2023\11\02"
		return str.replace("-", File.separator);
	}

	// 서버에 파일업로드 기능 및 실제업로드 한 파일명 반환
	/*
	   String uploadFolder : 서버측에 업로드 될 폴더 C:\\dev\\upload\\product\\
	   String dataFolder : 이미지파일을 저장할 날짜 폴더명  2023\11\03
	   MultipartFile uploadFile : 업로드된 파일을 참조하는 객체
	 */
	public static String uploadFile(String uploadFolder, String dataFolder, MultipartFile uploadFile) {

		String realUploadFileName = ""; // 실제 업로드한 파일이름(파일이름 중복방지)

//		File클래스 : 파일과 폴더관련작업하는 기능		
		File file = new File(uploadFolder, dataFolder); // 예> "C:/dev/upload" "2023/11/02"

		// 예> "C:/dev/upload" "2023/11/02" 폴더경로가 없으면, 폴뎌명을 생성하라
		if (file.exists() == false) {
			file.mkdirs();
		}

		String clientFileName = uploadFile.getOriginalFilename();

		// 파일명을 중복방지를 위하여 고유한 문자열을 만들어주는 UUID 클래스
		UUID uuid = UUID.randomUUID();

		// b39dbe2c-d2ac-41af-bd4e-50539ba0aeda_2.png
		realUploadFileName = uuid.toString() + "_" + clientFileName;

		try {
			// file : "C:/dev/upload" "2023/11/02" + realUploadFileName : 업로드할 파일명
			File saveFile = new File(file, realUploadFileName);
			// 파일업로드 (파일복사)
			uploadFile.transferTo(saveFile); // 파일업로드의 핵심메소드

			// Thumbnail 작업
			// 원본이미지가 파일크기가 커서, 브라우저에 리스트로 사용시 로딩되는 시간이 길어진다
			// 원본이미지를 파일크기와 해상도를 낮추어 작은형태의 이미지로 만드는 것.
			if (checkImageType(saveFile)) { // 첨부된 파일이 이미지 일 경우라면
				
				// 파일출력스트림 클래스
				// 밑에줄만 실행이 되면, 파일이 생성. 0KB.
				FileOutputStream thumbnail = new FileOutputStream(new File(file, "s_" + realUploadFileName));
				
				// 썸네일 작업기능 라이브러리에서 제공하는 클래스. pom.xml참고.
				// 원본이미지파일의 내용을 스트림방식으로 읽어서, 썸네일 이미지파일에 쓰는 작업
				// Thumbnailator.createThumbnail(원본이미지파일의 입력스트림, 썸네일파일객체, 100, 100);
				Thumbnailator.createThumbnail(uploadFile.getInputStream(), thumbnail, 100, 100);
				
				thumbnail.close();
				
			}

		} catch (Exception e) {
			e.printStackTrace(); // 파일 업로드시 예외가 발생되면, 예외정보를 출력
		}
		return realUploadFileName; // 상품테이블에 상품이미지명으로 저장.
	}

	// 자바스크립트로 업로드되는 파일의 확장자를 이용하여,
	// 업로드되는 파일구분(이미파일 또는 일반 파일 구분)
	private static boolean checkImageType(File saveFile) {

		boolean isImageType = false; // 변수의 값이 true면 이미지파일, false면 일반파일 구분작업

		try {
			// MIME : text/html, text/plain, image/jpeg, .....
			String contentType = Files.probeContentType(saveFile.toPath());

			// contentType 변수의 값이 image 문자열로 시작되는지 여부를 true or false 값으로 리턴
			isImageType = contentType.startsWith("image");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isImageType;
	}
	
	// 프로젝트 외푸폴더에서 관리되고 있는 상품이미지를 브라우저의 <img src="매핑주소"> 이미지태그로 부터 요청이 들어왔을 때 바이트배열로 보내주는 작업.
	// String uploadPath : 업로드 폴더경로
	// String fileName : 날짜폴더경로를 포함한 파일명. (db)
	// ResponseEntity 클래스 - 1)헤더(header) 2)바디(body)-데이터 3)상태코드
	public static ResponseEntity<byte[]> getFile(String uploadPath, String fileName) throws Exception {
		
		ResponseEntity<byte[]> entity = null;
		
		File file = new File(uploadPath, fileName); // 상품이미지파일을 참조하는 파일객체
		
		// 파일이 해당경로에 존재하지 않으면
		if(!file.exists()) {
			return entity; // null 로 리턴.
		}
		
		//1)Header
		//Files.probeContentType(file.toPath()) : image/jpeg
		// file : 6c13c2c7-60b3-4ae7-9076-ae3f54333fad_4.png
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", Files.probeContentType(file.toPath()));
		
		entity = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
		
		return entity;
	}
}
