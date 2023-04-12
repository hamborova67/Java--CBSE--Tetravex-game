package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;

import java.util.Arrays;
import java.util.List;

public class CommentServiceRestClient implements CommentService{
    //See value of remote.server.api property in application.properties file
    @Value("${remote.server.api}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public void addComment(Comment comment) throws CommentException {
        restTemplate.postForEntity(url + "/comment", comment, Comment.class);
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        return Arrays.asList(restTemplate.getForEntity(url + "/comment/" + game, Comment[].class).getBody());
    }

    @Override
    public void reset() throws CommentException {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
