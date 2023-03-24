package com.blog.services.impl;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PagePostResponse;
import com.blog.payloads.PostDTO;
import com.blog.repositories.CategoryRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepo postRepository;
    private final ModelMapper modelMapper;
    private final UserRepo userRepository;
    private final CategoryRepo categoryRepository;

    @Autowired
    public PostServiceImpl(PostRepo postRepository, ModelMapper modelMapper, UserRepo userRepository, CategoryRepo categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDTO createPost(PostDTO dto,Integer userId,Integer categoryId) {

        User user=userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));

        Post post=modelMapper.map(dto,Post.class);
        post.setImageName(dto.getImageName());
        post.setCreationDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post savePost = postRepository.save(post);
        return modelMapper.map(savePost, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO dto, Integer postId) {
        Post post=postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post","id",postId));

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setImageName(dto.getImageName());

        Post savePost = postRepository.save(post);
        return modelMapper.map(savePost, PostDTO.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post=postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        postRepository.delete(post);
    }

    @Override
    public List<PostDTO> getAllPost() {
        List<Post> all = postRepository.findAll();
        List<PostDTO> allPostDTO=new ArrayList<>();
        for(Post p:all){
            allPostDTO.add(modelMapper.map(p,PostDTO.class));
        }
        return allPostDTO;
    }

    @Override
    public PagePostResponse getAllPostPaginated(Integer pageNumber, Integer pageSize,String sortBy,String direction) {

        Sort sort;
        if(direction.equals("asc")){
            sort=Sort.by(sortBy).ascending();
        }else if(direction.equals("dsc")){
            sort=Sort.by(sortBy).descending();
        }else{
            sort=Sort.by(sortBy).ascending();
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> page = postRepository.findAll(pageable);
        List<PostDTO> DTOList = page.getContent()
                .stream()
                .map(p -> modelMapper.map(p, PostDTO.class))
                .collect(Collectors.toList());
        return new PagePostResponse(DTOList,pageNumber,pageSize,page.getTotalPages(),page.isLast());
    }

    @Override
    public PostDTO getPost(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        return modelMapper.map(post, PostDTO.class);
    }

    @Override
    public List<PostDTO> getAllByUser(Integer userId) {
        User user=userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        List<Post> allByUser = postRepository.getAllByUser(user);
        List<PostDTO> allPostDTO=new ArrayList<>();
        for(Post p:allByUser){
            allPostDTO.add(modelMapper.map(p,PostDTO.class));
        }
        return allPostDTO;
    }

    @Override
    public List<PostDTO> getAllByCategory(Integer categoryId) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
        List<Post> allByCategory = postRepository.getAllByCategory(category);
        List<PostDTO> allPostDTO=new ArrayList<>();
        for(Post p:allByCategory){
            allPostDTO.add(modelMapper.map(p,PostDTO.class));
        }
        return allPostDTO;
    }

    @Override
    public List<PostDTO> searchPost(String keyword) {
//        postRepository.searchByTitle("%"+keyword+"%");
        return postRepository.getAllByTitleContaining(keyword)
                .stream()
                .map(p -> modelMapper.map(p, PostDTO.class))
                .collect(Collectors.toList());
    }
}
