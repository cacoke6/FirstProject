package com.example.basic.Member.controller;


import com.example.basic.Member.model.Board;
import com.example.basic.Member.model.Comment;
import com.example.basic.Member.repositoty.BoardRepository;
import com.example.basic.Member.repositoty.CommentRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Optional;



@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    CommentRepositoy commentRepositoy;
    @Autowired
    HttpSession session;

    @PostMapping("/comment")
    public String comment(
            @ModelAttribute Comment comment,
            @RequestParam int boardId
    ) {
        String email = (String) session.getAttribute("email");

        if (email == null) {
            email = "Anonymous";
        }

        comment.setWriter(email);
        comment.setCreDate(new Date());
        Board board = new Board();
        board.setId(boardId);
        comment.setBoard(board);

        commentRepositoy.save(comment);

        return "redirect:/board/detail?id=" + boardId;
    }


    @GetMapping("/detail")
    public String detail(
            @RequestParam int id,
            Model model) {
        Optional<Board> opt =
                boardRepository.findById(id);
        model.addAttribute(
                "board",
                opt.get());

        return "board/detail";
    }

    @GetMapping("/comment/remove")

    public String commentRemove(
            @ModelAttribute Comment comment, int boardId
    ) {
        commentRepositoy.delete(comment);
        return "redirect:/board/detail?id=" + boardId;
    }

    @GetMapping("/remove")

    public String remove(
            @RequestParam int id
    ) {

        String loggedName = (String) session.getAttribute("email");

        Optional<Board> dbBoard = boardRepository.findById(id);

        String savedName = dbBoard.get().getWriter();

        if (savedName.equals(loggedName)) {
            Board board = new Board();
            board.setId(id);

            boardRepository.delete(board);
        } else {
            return "redirect:/board/detail?id=" + id;
        }


        return "redirect:/board/list";
    }

    @GetMapping("/update")
    public String update(@RequestParam int id, Model model) {
        Optional<Board> opt = boardRepository.findById(id);
        model.addAttribute("board", opt.get());
        return "board/update";
    }

    @PostMapping("/update")
    public String updatePost(@ModelAttribute Board board) {
        boardRepository.save(board);
        return "redirect:/board/list";
    }

    @GetMapping({"/list", "/", "pagination"})
    public String list(
            Model model,
            @RequestParam(defaultValue = "1") int page) {
        Sort.Direction dic = Sort.Direction.DESC;
        Sort sort = Sort.by(dic, "id");
        Pageable pages = PageRequest.of(page - 1, 1, sort);
        Page<Board> boardList = boardRepository.findAll(pages);

        int startPage = (page - 1) / 10 * 10 + 1;
        int endPage = startPage + 9;
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("page", page);
        model.addAttribute(
                "boardList", boardList);
        return "board/list";
    }

    @GetMapping("/write")
    public String boardWrite() {

        String email = (String) session.getAttribute("email");

        if (email == null || email.trim().equals("")) {

            return "redirect:/auth/signin";
        }

        return "/board/write";
    }

    @PostMapping("/wirte")
    public String boardWritePost(@ModelAttribute Board board) {
        String email = (String) session.getAttribute("email");

        board.setWriter(email);

        boardRepository.save(board);

        return "redirect:/board/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        boardRepository.deleteById(id);
        return "redirect:/board/list";
    }
}
