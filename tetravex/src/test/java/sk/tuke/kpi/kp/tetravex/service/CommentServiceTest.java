package sk.tuke.kpi.kp.tetravex.service;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.CommentServiceJDBC;
import sk.tuke.gamestudio.entity.Comment;


import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
public class CommentServiceTest
{
    private CommentService commentService = new CommentServiceJDBC();

    @Test
    public void testReset()
    {
        commentService.reset();
        assertEquals(0, commentService.getComments("tetra").size());
    }

    @Test
    public void testAddComment()
    {
        commentService.reset();
        var date = new Date();

        commentService.addComment(new Comment("tetravex", "nina", "Moze byt.", date));
        commentService.addComment(new Comment("tetravex", "mina", "Good.", date));
        commentService.addComment(new Comment("tetravex", "anna", "Ez.", date));
        commentService.addComment(new Comment("tetravex", "lila", "Like it.", date));

        var comments = commentService.getComments("tetravex");
        assertEquals(4, comments.size());

        assertEquals("nina", comments.get(0).getPlayer());
        assertEquals("tetravex", comments.get(0).getGame());
        assertEquals("Moze byt.", comments.get(0).getComment());
        assertEquals(date, comments.get(0).getCommentedOn());

        assertEquals("mina", comments.get(1).getPlayer());
        assertEquals("tetravex", comments.get(1).getGame());
        assertEquals("Good.", comments.get(1).getComment());
        assertEquals(date, comments.get(1).getCommentedOn());

        assertEquals("anna", comments.get(2).getPlayer());
        assertEquals("tetravex", comments.get(2).getGame());
        assertEquals("Ez.", comments.get(2).getComment());
        assertEquals(date, comments.get(2).getCommentedOn());
    }

    @Test
    public void testGetComments()
    {
        commentService.reset();
        var date = new Date();

        commentService.addComment(new Comment("tetravex", "nina", "Moze byt.", date));
        commentService.addComment(new Comment("tetravex", "mina", "Good.", date));
        commentService.addComment(new Comment("tetravex", "anna", "Ez.", date));
        commentService.addComment(new Comment("tetravex", "lila", "Like it.", date));

        var comments = commentService.getComments("tetravex");
        assertEquals(4, comments.size());

        assertEquals("nina", comments.get(0).getPlayer());
        assertEquals("tetravex", comments.get(0).getGame());
        assertEquals("Moze byt.", comments.get(0).getComment());
        assertEquals(date, comments.get(0).getCommentedOn());

        assertEquals("mina", comments.get(1).getPlayer());
        assertEquals("tetravex", comments.get(1).getGame());
        assertEquals("Good.", comments.get(1).getComment());
        assertEquals(date, comments.get(1).getCommentedOn());

        assertEquals("anna", comments.get(2).getPlayer());
        assertEquals("tetravex", comments.get(2).getGame());
        assertEquals("Ez.", comments.get(2).getComment());
        assertEquals(date, comments.get(2).getCommentedOn());
    }
}


