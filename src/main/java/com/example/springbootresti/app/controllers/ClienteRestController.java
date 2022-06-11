package com.example.springbootresti.app.controllers;

import com.example.springbootresti.app.models.entity.Cliente;
import com.example.springbootresti.app.models.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

//API REST - sino ponemos methods permite todos los tipos de como patch,get,etc
//@CrossOrigin(origins = {"http://localhost:4200"},methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@CrossOrigin(origins = {"${app.api.settings.cross-origin.urls}"},methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@RestController
@RequestMapping("/api")
public class ClienteRestController {

    @Autowired
    IClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> index(){
        return clienteService.findAll();
    }

    @GetMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.OK) //Esto no es necesario ya que es HttpStatus.OK por defecto
    public Cliente show(@PathVariable long id){
        return clienteService.findById(id);
    }

    /** Se usa el @RequestBody para mapear el cliente le ponemos HttpStatus.CREATED(201) ya que por defecto es HttpStatus.OK(200)**/
    @PostMapping("/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente create(@RequestBody Cliente cliente){
        System.out.println("39:id-> "+cliente.getId());
        //cliente.setCreateAt(new Date());
          return clienteService.save(cliente);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/clientes/{id}")
    public Cliente update(@RequestBody Cliente cliente,@PathVariable long id){
        Cliente clienteActual=clienteService.findById(id);
        clienteActual.setNombre(cliente.getNombre());
        clienteActual.setApellido(cliente.getApellido());
        clienteActual.setEmail(cliente.getEmail());
        return clienteService.save(clienteActual);
    }

    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/clientes/{id}")
    public void delete(@PathVariable long id){
        clienteService.delete(id);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/clientes/delAll")
    public List<Cliente> deleteAllClients(){
        clienteService.deleteAll();
        //Algo interesante es que si hacemos un deleteAll y despues un findAll jpa me devuelve una lista vacia pero no null
        return clienteService.findAll();
    }
    
    
    @GetMapping("/host")
    public List<Object> getHost(HttpServletRequest request){
       String r1=request.getRemoteHost();
       String r2 = request.getHeader("Host");
        Enumeration<String> enums=request.getHeaderNames();
        List<String> listE=new ArrayList<>();
       HeadersSpring hS= new HeadersSpring();
        while (enums.hasMoreElements()){
            hS.user_agent=hS.user_agent==null?request.getHeader(enums.nextElement()):hS.user_agent;
            hS.accept=hS.accept==null?request.getHeader(enums.nextElement()):hS.accept;
            hS.postman_token=hS.postman_token==null?request.getHeader(enums.nextElement()):hS.postman_token;
            hS.host=hS.host==null?request.getHeader(enums.nextElement()):hS.host;
            hS.accept_encoding=hS.accept_encoding==null?request.getHeader(enums.nextElement()):hS.accept_encoding;
            hS.connection=hS.connection==null?request.getHeader(enums.nextElement()):hS.connection;
            //listE.add(enums.nextElement());
       }

       return List.of(r1,r2,hS);
    }
}
class HeadersSpring {
    public String user_agent;
    public String accept;
    public String postman_token;
    public String host;
    public String accept_encoding;
    public String connection;

}

