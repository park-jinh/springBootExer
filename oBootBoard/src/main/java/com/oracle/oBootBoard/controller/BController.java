package com.oracle.oBootBoard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oracle.oBootBoard.command.BExecuteCommand;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BController {
	
	private static final Logger logger = LoggerFactory.getLogger(BController.class);

	private final BExecuteCommand bExecuteCommand;
	@Autowired
	public BController(BExecuteCommand bExecuteCommand) {
		this.bExecuteCommand = bExecuteCommand;
	}
	
	@RequestMapping("list")
	public String list(Model model) {
		logger.info("list start...");
		bExecuteCommand.bListCmd(model);
		return "list";
	}
	
	@RequestMapping("/write_view")
	public String write_view(Model model) {
		logger.info("write_view start...");
		return "write_view";
	}
	
	@PostMapping(value = "/write")
	public String write(HttpServletRequest request, Model model) {
		logger.info("write start...");
		
		model.addAttribute("request",request);
		bExecuteCommand.bWriteCmd(model);
		return "redirect:list";
	}
	
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request, Model model) {
		System.out.println("content_view()");
		
		model.addAttribute("request", request);
		bExecuteCommand.bContentCmd(model);
		
		return "content_view";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(HttpServletRequest request, Model model) {
		logger.info("modify start...");
		model.addAttribute("request", request);
		bExecuteCommand.bModifyCmd(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("/reply_view")
	public String reply_view(HttpServletRequest request, Model model) {
		System.out.println("reply_view start...");
		
		model.addAttribute("request",request);
		bExecuteCommand.bReplyViewCmd(model);
		return "reply_view";
	}
	
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public String reply(HttpServletRequest request, Model model) {
		System.out.println("reply()");
		
		model.addAttribute("request", request);
		bExecuteCommand.bReplyCmd(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		System.out.println("delete()");
		
		model.addAttribute("request", request);
		bExecuteCommand.bDeleteCmd(model);
		
		return "redirect:list";
	}
}
