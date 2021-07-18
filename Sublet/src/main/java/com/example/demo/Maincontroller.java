package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;


@CrossOrigin(origins = "http://localhost:3000")
@RestController // This means that this class is a Controller
//@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class Maincontroller {
	 @Autowired // This means to get the bean called userRepository
   
     private UserRepository userRepository;
	 
	 @Autowired
	 private PostRepository postrepo;
	 
	 @Autowired 
	 AuthenticationManager authenticationManager;
	 
	 @Autowired
	 private JwtUtils JwtTokenUtil;

	 //commented 23-6
//	@GetMapping(path="/")
//	public String viewHomePage() {
//		 return "index";
//	 }
	 



	 //commented 23-6
//	 @GetMapping(value = "/login")
//	    public String login() {
//	        return "login";
//	    }
//	 
//	 @GetMapping(value = "/login-error")
//	    public String loginerror(Model model) {
//	        model.addAttribute("loginError", true);
//	        return "login";
//	    }
	 
	 
@PostMapping("/auth/signin")
public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{

	Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));

	SecurityContextHolder.getContext().setAuthentication(authentication);
	String jwt = JwtTokenUtil.generateJwtToken(authentication);
	
	CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();		

	return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getId(),userDetails.getUsername()));
	
}

	 //commented 23-6
//@PostMapping("/signin")
//public ResponseEntity<?> authenticateUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
//
//	AuthenticationRequest authrequest= new ObjectMapper().readValue(request.getInputStream(),AuthenticationRequest.class);
//	Authentication authentication = authenticationManager.authenticate(
//			new UsernamePasswordAuthenticationToken(authrequest.getEmail(), authrequest.getPassword()));
//
//	SecurityContextHolder.getContext().setAuthentication(authentication);
//	String jwt = JwtTokenUtil.generateJwtToken(authentication);
//	
//	CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();		
//
////	response.addHeader("Authorization","Bearer"+ jwt); change to void
//	return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getUsername()));
//	//return ResponseEntity.ok(new AuthenticationResponse(jwt));
//	
//}

//commented 23-6
//@PostMapping("/signup")
////ResponseEntity<?>
//public String registerUser(@ModelAttribute User user) {
//
//	if (userRepository.existsByEmail(user.getEmail())) {
//		return "redirect:/register?error";
////				ResponseEntity
////				.badRequest()
////				.body(new MessageResponse("Error: Email is already in use!"));
//	}
//	BCryptPasswordEncoder encoder = new  BCryptPasswordEncoder();
//	// Create new user's account
////	User user = new User(authenticationRequest.getEmail(),
////						 encoder.encode(authenticationRequest.getPassword()));
//	
//	String encodedPassword = encoder.encode(user.getPassword());
//	user.setPassword(encodedPassword);
//	userRepository.save(user);
////	return "signup?success";
//	return "register_success";
////	return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//}

@PostMapping("/auth/signup")
public ResponseEntity<?> registerUser(@RequestBody AuthenticationRequest signUpRequest) {

	if (userRepository.existsByEmail(signUpRequest.getEmail())) {
		return ResponseEntity
				.badRequest()
				.body(new MessageResponse("Error: Email is already in use!"));
	}
	BCryptPasswordEncoder encoder = new  BCryptPasswordEncoder();
	// Create new user's account
	User user = new User(signUpRequest.getEmail(),
						 encoder.encode(signUpRequest.getPassword()));

	userRepository.save(user);

	return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
}
//get posts by location
@GetMapping("/public/posts")
public ResponseEntity<List<Post>> getAllPosts(@RequestParam(required = true) String location) {
	try {
	      List<Post> posts = new ArrayList<Post>();
	      
	      postrepo.findByLocation(location).forEach(posts::add);

//	      if (title == null)
//	        tutorialRepository.findAll().forEach(tutorials::add);
//	      else
//	        tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);

	      if (posts.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      return new ResponseEntity<>(posts, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}

//get user posts 
@GetMapping("/{user_id}/posts")
public ResponseEntity<List<Post>> getAllUserPosts(@PathVariable("user_id") Integer id) {
	try {
          System.out.println("cled");
	      Optional <User> user = userRepository.findById(id);
	  	if(!user.isPresent())  
			{  
			throw new UserNotFoundException("id: "+ id);  
			}  
	     
	  	List<Post> posts = user.get().getPosts();
	  	System.out.println("p" +  posts);
	      if (posts.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      return new ResponseEntity<>(posts, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}

// get user post by id
@GetMapping("/{user_id}/posts/{id}")
public ResponseEntity<Post> getPostById(@PathVariable("user_id") Integer u_id,@PathVariable("id") Integer id) {
	  Optional<Post> postData = postrepo.findById(id);
	  Optional <User> user = userRepository.findById(u_id);
	  User _user =  postData.get().getUser();
	  System.out.println(_user.equals(user.get()));
	    if (postData.isPresent() && _user.equals(user.get())) {
	      return new ResponseEntity<>(postData.get(), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
}

//create posts of user
@PostMapping("/{user_id}/posts")
public ResponseEntity<Post> createPost(@PathVariable("user_id") Integer id, @RequestBody Post post) {
	try {
		
		Optional<User> user= userRepository.findById(id);
		if(!user.isPresent())  
		{  
		throw new UserNotFoundException("id: "+ id);  
		}       
		User _user=user.get();     
		//map the user to the post  
		post.setUser(_user);	
		Post _post = postrepo.save(post);
	    return new ResponseEntity<>(_post, HttpStatus.CREATED);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}

//update post
@PutMapping("/posts/{id}")
public ResponseEntity<Post> updatePost(@PathVariable("id") Integer id, @RequestBody Post post) {
	Optional<Post> postData = postrepo.findById(id);

    if (postData.isPresent()) {
    	Post _post = postData.get();
      _post.setTitle(post.getTitle());
      _post.setDescription(post.getDescription());
      return new ResponseEntity<>(postrepo.save(_post), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

//delete post
@DeleteMapping("/posts/{id}")
public ResponseEntity<HttpStatus> deletePost(@PathVariable("id") Integer id) {
	try {
	      postrepo.deleteById(id);
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }

}





@GetMapping(path="/register")
public String viewSignupForm(Model model)
{
	model.addAttribute("user", new User());
	return "signup";
}

@PostMapping("/process_register")
public String processRegister(User user)
{
	BCryptPasswordEncoder encoder = new  BCryptPasswordEncoder();
	String encodedPassword = encoder.encode(user.getPassword());
	user.setPassword(encodedPassword);
	userRepository.save(user);
	return "register_success";
//	return "redirect:/process_register?success";	
}

@GetMapping("/list_users")
public String viewUserList(Model model)
{ 
	List<User> listUsers = userRepository.findAll();//findByUserId(request.getUserPrincipal().);
    model.addAttribute("listUsers",listUsers);
	return "users";
}

@GetMapping("/list_posts")
public String viewPostList(Model model)
{ 
//	System.out.println(model);
//	System.out.println(request.getUserPrincipal());
//	System.out.println(request.getRemoteUser());
	List<Post> listPosts = postrepo.findAll();//findByUserId(request.getUserPrincipal().);
    model.addAttribute("listPosts",listPosts);
	return "posts";
}

//commented23-6
//@GetMapping("/users/{id}/list_Posts")
//public List<Post> viewUserPostList(@PathVariable int id, Model model)
//{ 
////	System.out.println(model);
////	System.out.println(request.getUserPrincipal());
////	System.out.println(request.getRemoteUser());
//	Optional <User> user = userRepository.findById(id);
//	if(!user.isPresent())  
//			{  
//			throw new UserNotFoundException("id: "+ id);  
//			}  
//	List<Post> listPosts = user.get().getPosts();
////findByUserId(request.getUserPrincipal().);
//    model.addAttribute("listPosts",listPosts);
//	return "posts";
//}

@GetMapping(path="/all")
public @ResponseBody Iterable<User> getAllUsers() {
// This returns a JSON or XML with the users
return userRepository.findAll();
}

//commented
@GetMapping(path="/all_posts")
public String viewPostsList(@RequestParam(value="location") String loc, Model model)
{
	
	List<Post> listPosts = postrepo.findByLocation(loc);
	model.addAttribute("listPosts",listPosts);
	return "posts";
}


@GetMapping("posts/edit/{id}")
public String showeditForm(@PathVariable("id") Integer id, Model model) {
  Post post = postrepo.findById(id).get();
  
  List<String> util = Arrays.asList("Electricity","Heat","Laundary","Wifi");
	List<String> loc = Arrays.asList("Halifax","Dartmouth","Truro");
	model.addAttribute("util",util);
	model.addAttribute("loc",loc);
	model.addAttribute("post",post);
	return "post_form";
}

@GetMapping("/posts/delete/{id}")
public String showdeleteForm(@PathVariable("id") Integer id, Model model) {
   postrepo.deleteById(id);
	return "redirect:/list_posts";
}


//commented
@GetMapping(path="/pos")
public String showpostform(Model model)
{
	model.addAttribute("post", new Post());
	List<String> util = Arrays.asList("Electricity","Heat","Laundary","Wifi");
	List<String> loc = Arrays.asList("Halifax","Dartmouth","Truro");
	model.addAttribute("util",util);
	model.addAttribute("loc",loc);
	return "post_form";
}
//
@PostMapping(path="users/{id}/posts/save")
public String savePost(Model model, Post post, @PathVariable int id)
{   System.out.println(post.toString());
Optional<User> userOptional= userRepository.findById(id);  
if(!userOptional.isPresent())  
{  
throw new UserNotFoundException("id: "+ id);  
}  
User user=userOptional.get();     
//map the user to the post  
post.setUser(user);
	postrepo.save(post);
//	System.out.println(model);
//	List<Post> listPosts = postrepo.findAll();//findByUserId(request.getUserPrincipal().);
//    model.addAttribute("listPosts",listPosts);
	return "redirect:/list_posts";
}

}
