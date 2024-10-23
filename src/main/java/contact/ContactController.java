package contact;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

@WebServlet("/contactAPP")
public class ContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ContactDAO dao;
	private ServletContext ctx;
	private final String START_PAGE = "contact/loginPage.jsp";
	
//	init
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = new ContactDAO();
		ctx = getServletContext();
	}
	
//	service
	@Override
	protected void service(HttpServletRequest req
			, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String action = req.getParameter("action");
		Method method;
		String view = null;
		
		if (action == null) {
			action = "loginPage";
		}
		
		try {
			method = this.getClass().getMethod(action, HttpServletRequest.class);
			view = (String)method.invoke(this, req);
		}catch (NoSuchMethodException e) {
			e.printStackTrace();
			ctx.log("요청한 action 없음");
			view = START_PAGE;
			req.setAttribute("error", "잘못된 요청 정보");
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		if (view.startsWith("redirect:/")) {
			String rview = view.substring("redirect:/".length());
			resp.sendRedirect(rview);
		} else {
			RequestDispatcher disp = req.getRequestDispatcher(view);
			disp.forward(req, resp);
		}
		
		
	}
	
//	목록보기 메소드
	public String listContacts(HttpServletRequest request) {
		ArrayList<ContactDTO> list	= new ArrayList<ContactDTO>();
		HttpSession httpSession = request.getSession();
		httpSession.getAttribute("userName");
		if (httpSession.getAttribute("sessionID") == null) {
			return "contactAPP?action=login";
		}
		
		try {
			list = dao.findAll();
			request.setAttribute("contactList", list);
		} catch (Exception e) {
			ctx.log("연락처 목록 생성 문제 발생");
			request.setAttribute("error", "불러오기 오류 발생");
		}
		return "contact/mainPage.jsp";
	}

//	검색 메소드
	public String search(HttpServletRequest request) {
		ArrayList<ContactDTO> list = new ArrayList<ContactDTO>();
		String name = request.getParameter("name");
		System.out.println(name);
		
		HttpSession httpSession = request.getSession();
		
		if (httpSession.getAttribute("sessionID") == null) {
			return "contactAPP?action=login";
		}
		
		try {
			list = dao.findById(name);
			request.setAttribute("contactList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "contact/mainPage.jsp";
	}
	
//	회원 추가 메소드
	public String insert(HttpServletRequest request) {
		ContactDTO contactDTO = new ContactDTO();
		
		try {
			BeanUtils.populate(contactDTO, request.getParameterMap());
			dao.insert(contactDTO);
			} catch (Exception e) {
				e.printStackTrace();
				ctx.log("회원 추가 에러");
				request.setAttribute("error", "회원 추가 에러");
				
				return listContacts(request);
			}
		
		return "redirect:/contactAPP?action=listContacts";
	}
	
//	회원 삭제 메소드
	public String delete(HttpServletRequest request) {
		String id = request.getParameter("id");
		HttpSession session = request.getSession();
		
		try {
			dao.delete(id);
			session.setAttribute("complete", "삭제가 완료되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("회원 삭제 오류 발생");
			request.setAttribute("error", "회원 삭제 오류 발생");
			
			return listContacts(request);
		}
		
		return "redirect:/contactAPP?action=listContacts";
	}

//	회원 수정 메소드
	public String update(HttpServletRequest request) {
		ContactDTO contactDTO = new ContactDTO();
		HttpSession session = request.getSession();
		
		try {
			BeanUtils.populate(contactDTO, request.getParameterMap());
			dao.Update(contactDTO);
			session.setAttribute("complete", "수정이 완료되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("회원 수정 오류 발생");
			request.setAttribute("error", "회원 수정 오류 발생");
		}
		
		return "redirect:/contactAPP?action=listContacts";
	}
	
//	회원 가입 메소드
	public String register(HttpServletRequest request) {
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		int reg = 0;
		
		try {
			BeanUtils.populate(userInfoDTO, request.getParameterMap());
			reg = dao.register(userInfoDTO);
			if (reg == 0) {
				ctx.log("등록된 아이디입니다.");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("회원가입 에러");
			request.setAttribute("error", "이미 등록된 아이디입니다.");
		}
		
		return "contact/loginPage.jsp";
	}
	
	
//	로그인 메소드
	public String login(HttpServletRequest request) {
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		String userName = null;
			
		try {
			BeanUtils.populate(userInfoDTO, request.getParameterMap());
			userName = dao.login(userInfoDTO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (userName == null) {
			ctx.log("로그인 실패");
			request.setAttribute("error", "로그인 실패");
			
			return "contact/loginPage.jsp";
		} else {
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("sessionID", userInfoDTO.getUser_id());
			httpSession.setAttribute("userName", userName);
			request.setAttribute("sessionID", dao.getSessionID(httpSession));
			request.setAttribute("userName", userName);
			return "contactAPP?action=listContacts";
		}
	}

//	로그아웃 메소드
	public String logout(HttpServletRequest request) {
		request.setAttribute("sessionID", null);
		
		return START_PAGE;
	}
	
//	로그인페이지 보기
	public String loginPage(HttpServletRequest request) {
		return START_PAGE;
	}
	
}
