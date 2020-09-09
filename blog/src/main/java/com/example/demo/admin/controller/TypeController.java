package com.example.demo.admin.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.TypeService;
import com.example.demo.vo.Type;

@Controller
@RequestMapping("/admin")
public class TypeController {

	@Autowired
	TypeService typeservice;

	@GetMapping("/types")
	public String list(@PageableDefault(size = 3, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable,
			Model model) {

		model.addAttribute("page", typeservice.listType(pageable));
		return "admin/types";
	}

	@GetMapping("/types/input")
	public String input(Model model) {
		model.addAttribute("type", new Type());
		return "/admin/types-input";
	}

	@GetMapping("/types/{id}/input")
	public String editInput(@PathVariable long id, Model model) {
		model.addAttribute("type", typeservice.getType(id));
		return "/admin/types-input";
	}

	// 分類保存提交
	@PostMapping("/types")
	public String post(@Valid Type type, BindingResult result, RedirectAttributes addrAttributes) {
		Type t = typeservice.getTypeByName(type.getName());

		if (t != null) {
			result.rejectValue("name", "nameError", "此名稱已存在");
		}
		if (result.hasErrors()) {
			return "admin/types-input";
		}
		Type type1 = typeservice.saveType(type);

		if (type1 == null) {
			addrAttributes.addFlashAttribute("message", "新增失敗");
		} else {
			addrAttributes.addFlashAttribute("message", "新增成功");
		}

		return "redirect:/admin/types";
	}

	// 分類保存提交
	@PostMapping("/types/{id}")
	public String editpost(@Valid Type type, BindingResult result, @PathVariable Long id,
			RedirectAttributes addrAttributes) {
		Type t = typeservice.getTypeByName(type.getName());

		if (t != null) {
			result.rejectValue("name", "nameError", "此名稱已存在");
		}
		if (result.hasErrors()) {
			return "admin/types-input";
		}
		Type type1 = typeservice.updateType(id, type);

		if (type1 == null) {
			addrAttributes.addFlashAttribute("message", "更新失敗");
		} else {
			addrAttributes.addFlashAttribute("message", "更新成功");
		}

		return "redirect:/admin/types";
	}
	
	@GetMapping("/types/{id}/delete")
	public String delete(@PathVariable long id,RedirectAttributes addrAttributes) {
		typeservice.deleteType(id);
		addrAttributes.addFlashAttribute("message","刪除成功");
		return "redirect:/admin/types";
	}
	
	

}
