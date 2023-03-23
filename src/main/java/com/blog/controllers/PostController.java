package com.blog.controllers;

import com.blog.exceptions.ResourceNotProvidedException;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.AppConstants;
import com.blog.payloads.PagePostResponse;
import com.blog.payloads.PostDTO;
import com.blog.services.FileService;
import com.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;
    private final FileService fileService;
    @Value("${project.image}")
    private String path;

    @Autowired
    public PostController(PostService postService, FileService fileService) {
        this.postService = postService;
        this.fileService = fileService;
    }

    @PostMapping("/category/{categoryId}/user/{userId}")
    public ResponseEntity<PostDTO> createPost(
            @Valid @RequestBody PostDTO dto,
            @PathVariable("categoryId") Integer categoryId,
            @PathVariable("userId") Integer userId,
            @RequestParam(value = "image",required = false) MultipartFile file
            ) throws IOException, ResourceNotProvidedException {
        //set image file name
        if(file != null){
                String fileName = fileService.uploadFile(path, file);
                dto.setImageName(fileName);
        }else{
            dto.setImageName("default.png");
        }

        PostDTO postDTO = postService.createPost(dto, userId, categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(postDTO);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable("userId") Integer userId){
        List<PostDTO> allByUser = postService.getAllByUser(userId);
        return ResponseEntity.ok(allByUser);
    }

    @GetMapping("/category/{category}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable("category") Integer category){
        List<PostDTO> allByCategory = postService.getAllByCategory(category);
        return ResponseEntity.ok(allByCategory);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable("postId") Integer postId){
        PostDTO post = postService.getPost(postId);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/")
    public ResponseEntity<List<PostDTO>> getAllPost(){
        List<PostDTO> allPost = postService.getAllPost();
        return ResponseEntity.ok(allPost);
    }

    @GetMapping("/page")
    public ResponseEntity<PagePostResponse> getPostPaginated(
            @RequestParam(
                    value = "pageNumber",
                    defaultValue = AppConstants.PAGE_NUMBER,
                    required = false) Integer number,
            @RequestParam(
                    value = "pageSize",
                    defaultValue = AppConstants.PAGE_SIZE,
                    required = false) Integer size,
            @RequestParam(
                    value = "sortBy",
                    defaultValue = AppConstants.PAGE_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(
                    value = "direction",
                    defaultValue = AppConstants.PAGE_DIRECTION,
                    required = false) String direction
            ){
        PagePostResponse response = postService.getAllPostPaginated(number, size,sortBy,direction);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable("postId") Integer postId){
        //todo : delete image
        postService.deletePost(postId);
        return ResponseEntity.ok(new ApiResponse("Post deleted successfully.",true));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> updatePost(
            @RequestBody PostDTO dto,
            @PathVariable("postId") Integer postId
    ){
        PostDTO postDTO = postService.updatePost(dto, postId);
        return ResponseEntity.ok(postDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostDTO>> searchPost(@RequestParam("keywordInTitle") String keyword){
        List<PostDTO> DTOList = postService.searchPost(keyword);
        return ResponseEntity.ok(DTOList);
    }

    @PostMapping("/{postId}/upload")
    public ResponseEntity<PostDTO> uploadPostFile(
            @RequestParam("file") MultipartFile file,
            @PathVariable("postId") Integer postId)
            throws IOException, ResourceNotProvidedException {
        PostDTO postDTO = postService.getPost(postId);

        String uploadFile = fileService.uploadFile(path, file);
        postDTO.setImageName(uploadFile);
        PostDTO updatedPost = postService.updatePost(postDTO, postId);
        return ResponseEntity.ok(updatedPost);
    }

    @GetMapping(value = "/download/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response)
            throws IOException{

        InputStream inputStream = fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream,response.getOutputStream());
    }
}