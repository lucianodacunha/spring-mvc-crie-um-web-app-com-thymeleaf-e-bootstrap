# Spring MVC: crie um web app com Thymeleaf e Bootstrap

## Aula 1: Conhecendo o Spring MVC

- Para gerar o projeto usamos o Spring Initialzr: https://start.spring.io/
- Usamos o Spring Boot para inicializar a aplicação e rodar o Tomcat
- Spring Boot já tem o servlet container Tomcat embutido
- Spring MVC segue o padrão arquitetural Model-View-Controller
- O servlet do Spring MVC recebe as requisições e delega para controllers mais específicos
- Como View, o Spring MVC usa Thymeleaf
- O Thymeleaf é uma alternativa a tecnologia JSP, serve para gerar HTML dinamicamente
- Thymeleaf tem a vantagem de usar atributos para definir a expression language
- Para definir uma classe que responde uma requisição HTTP, usamos as anotações:
  - @Controller, @GetMapping entre outras opções
- Métodos que atendem requisições HTTP são chamados de action

## Aula 2: Thymeleaf e Bootstrap

- Como usar um modelo nosso para a view
- Como iterar com Thymeleaf e trabalhar com inputs e imagens
- Como aplicar classes do Bootstrap e posicionar elementos de maneira responsiva

## Aula 3: Integração com Spring Data

- A integrar o módulo Spring Data JPA
  - Configuramos as dependências no pom.xml
  - Configuramos os dados da conexão pelo application.properties
- Que, para usar o Spring Data JPA, basta criar uma interface e:
  - Usar a anotação @Repository
  - Estender uma interface do Spring como JpaRepository
- Que um repositório padrão do Spring já tem vários métodos implementados. como findAll, save, delete ou findById
- Que, para receber uma instância do repositório, usamos a injeção de dependências

### MariaDB com Docker

Docker é uma das tecnologias mais utilizadas para rodar aplicações e processos em geral na nuvem ou local. Ele é ideal para, por exemplo, executar um banco de dados sem se preocupar com a instalação, tudo usando containers que empacotam toda a instalação.

E se você olhar no repositório no GitHub que acompanha esse curso, você vai encontrar um arquivo docker-compose.yml:

[https://github.com/alura-cursos/1530-springmvc-parte1](https://github.com/alura-cursos/1530-springmvc-parte1)

Nele, está configurado o uso de dois containers, justamente relacionados com o banco de dados:

- Um container para rodar MariaDB na versão 10.5
- Um container para rodar adminer para acessar o banco a partir do navegador

Se você nunca ouviu falar de Docker, primeiro assista ao nosso curso:

[https://cursos.alura.com.br/course/docker-e-docker-compose](https://cursos.alura.com.br/course/docker-e-docker-compose)

Para quem já conhece Docker e tem instalado, basta executar na raiz da aplicação:
```
docker-compose up -d
```

### ModelAndView

Aprendemos que, para a view funcionar, é preciso receber o modelo através do Model que recebemos como parâmetro e é preciso definir o nome da view no retorno do método. Veja a estrutura básica da nossa action:
```
@GetMapping("/home")
public String home(Model model) {
    List<Pedido> pedidos = repository.findAll();
    model.addAttribute("pedidos", pedidos);
    return "home"; 
}
```
Ou seja, para trabalhar com uma página, é preciso definir um ModeloEPagina e para tal existe uma classe justamente com esse nome: ModelAndView. Veja a mesma action, mas usando ModelAndView do Spring MVC:
```
@GetMapping("/home")
public ModelAndView home() {
    List<Pedido> pedidos = repository.findAll();
    ModelAndView mv = new ModelAndView("home");
    mv.addObject("pedidos", pedidos);
    return mv; 
}   
```
Desse forma, os parâmetros do método ficam reservados para os dados da requisição (o input) e o retorno do método fica reservado para os dados da view (o output).

Você vai encontrar ambos os exemplos na literatura e nos projetos com Spring MVC!

### Mais sobre a injeção

A famosa injeção de dependências é o nome que damos para quando temos uma classe (por exemplo, nossa HomeController), que depende de outra classe (por exemplo, um repositório), e um mecanismo qualquer consegue descobrir e instanciar todas as dependências, e por fim, nos entregar o objeto que queremos.

Em outras palavras, delegamos a responsabilidade de criar um objeto para um framework. Repare que não é tão simples criar um repository, precisa de uma conexão, da EntityManagerFactory, do EntityManager, etc. Isso não importa no nosso código, pois o framework assume essa responsabilidade.

Frameworks de injeção de dependência são bem comuns no mundo Java. O Spring você já conhece. Pesquise também pelo Guice, feito pelo Google, e o CDI, do Jakarta EE.

- [Injeção de depências](https://cursos.alura.com.br/extra/alura-mais/injecao-de-dependencias-o-que-e--c224)

## Aula 4: Trabalhando com formulário

- Trabalhar com os componentes jumbotron do Bootstrap
- Usar o elemento nav do HTML5
- Usar a anotação @RequestMapping para definir uma parte da rota (entre outras possibilidades)
- Usar o padrão DTO (Data Transfer Object) para receber dados da requisição HTTP
  - Para tal, o nome do input HTML precisa ser igual aos atributos (getter e setter) do DTO
- Usar o JpaRepository para salvar uma entidade

### @RequestMapping

Usamos a anotação para definir uma parte da rota na nossa classe PedidoController:
```
@Controller
@RequestMapping("pedido")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("formulario")
    public String formulario() {
        return "pedido/formulario";
    }

    @PostMapping("novo")
    public String novo(RequisicaoNovoPedido requisicao) {
        Pedido pedido = requisicao.toPedido();
        pedidoRepository.save(pedido);

        return "pedido/formulario";
    }

}
```
A anotação @RequestMapping vai muito além da configuração da rota, ela na verdade é muito poderosa e permite definir detalhes sobre o mapeamento da requisição. Veja a mesma classe, mas usando apenas @RequestMapping:
```
@Controller
@RequestMapping("pedido")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @RequestMapping(method = RequestMethod.GET, value="formulario")
    public String formulario() {
        return "pedido/formulario";
    }

    @RequestMapping(method = RequestMethod.POST, value="novo")
    public String novo(RequisicaoNovoPedido requisicao) {
        Pedido pedido = requisicao.toPedido();
        pedidoRepository.save(pedido);

        return "pedido/formulario";
    }

}
```
Veremos ainda mais sobre essa anotação e como definir detalhes como cabeçalho, variáveis da URL, etc, durante os próximos cursos.

## Aula 5. Trabalhando com Bean Validation

- Que Bean Validation usa anotações para definir as regras de validação como @NotBlank ou @Pattern
- Para realmente executar as validações, devemos usar a anotação @Valid no método do controller
- Para saber se existem erros de validação ou não, existe o objeto do tipo BindingResult
- Para mostrar os erros de validação, o Thymeleaf possui atributos como th:object, th:field e th:errorClass

### Mais anotações de validação

Usamos a anotação @NotBlank, mas existem várias outras anotações que podem ser usadas e combinadas. Exemplos clássicos são @Email, @Min, @Max, @Size ou @Pattern.

Por exemplo, poderíamos definir que o nome do produto deve ter no mínimo 5 e no máximo 20 caracteres:
```
public class RequisicaoNovoPedido {

    @NotBlank @Size(min = 5, max = 20)
    private String nomeProduto; 

}
```
Para ver todas as anotações segue o link da documentação:

[https://docs.jboss.org/hibernate/beanvalidation/spec/2.0/api/javax/validation/constraints/package-summary.html](https://docs.jboss.org/hibernate/beanvalidation/spec/2.0/api/javax/validation/constraints/package-summary.html)

### Mais anotações de validação

Usamos a anotação @NotBlank, mas existem várias outras anotações que podem ser usadas e combinadas. Exemplos clássicos são @Email, @Min, @Max, @Size ou @Pattern.

Por exemplo, poderíamos definir que o nome do produto deve ter no mínimo 5 e no máximo 20 caracteres:
```
public class RequisicaoNovoPedido {

    @NotBlank @Size(min = 5, max = 20)
    private String nomeProduto; 

}
```
Para ver todas as anotações segue o link da documentação:

[https://docs.jboss.org/hibernate/beanvalidation/spec/2.0/api/javax/validation/constraints/package-summary.html](https://docs.jboss.org/hibernate/beanvalidation/spec/2.0/api/javax/validation/constraints/package-summary.html)

## Aula 6: Trabalhando com Templates

- Como criar um arquivo base para definir pedaços de HTML, com o atributo th:fragment
- Como importar os fragmentos, usando th:replace

O th:replace, que substitui a tag que foi utilizada:

```
<div th:replace="~{base :: titulo('Meus Pedidos')}"></div>
```
Existe um atributo "irmão" bem parecido, o th:insert. Veja o exemplo:
```
<div th:insert="~{base :: titulo('Meus Pedidos')}"></div>
```
A aplicação é idêntica, com a diferença que o th:insert não substitui a div original e sim joga todo o conteúdo dentro dela.

## Aula 7: Modelango o Status

- Como usar um switch no Thymeleaf, usando a tag th:block com o atributo th:switch e th:case
- Como adicionar uma classe dinamicamente com th:classappend
- Como formatar números e data através dos helpers do Thymeleaf
    - Usamos #numbers.formatDecimal(..) para formatar o número decimal
    - Usamos #temporals.format((..) para formatar a data
    - Existem vários outros helpers na documentação
- Como fazer um redirecionamento pelo Spring MVC, usando o prefixo redirect na action
- Como fazer o tratamento de erro usando o método onError
- Como trabalhar com @PathVariable e enumerações

### Forward vs redirect

O prefixo redirect na action:
```
@PostMapping("novo")
public String novo(@Valid RequisicaoNovoPedido requisicao, BindingResult result) {
    if(result.hasErrors()) {
        return "pedido/formulario";
    }

    Pedido pedido = requisicao.toPedido();
    pedidoRepository.save(pedido);

    return "redirect:/home";
}
```
Nesse caso, o Spring MVC executará o redirecionamento client-side. Isto é, ele devolve uma resposta HTTP para pedir uma nova requisição para a URL /home.

Também existe o redirecionamento server-side, mas devemos usar o prefixo forward. Veja o código:
```
@PostMapping("novo")
public String novo(@Valid RequisicaoNovoPedido requisicao, BindingResult result) {
    if(result.hasErrors()) {
        return "pedido/formulario";
    }

    Pedido pedido = requisicao.toPedido();
    pedidoRepository.save(pedido);

    return "forward:/home";
}
```
Nesse caso, o fluxo volta apenas para o Front-Controller do Spring MVC e ele chama a nova action. Enquanto o redirecionamento client-side causa uma nova requisição, o server-side continua dentro da mesma requisição HTTP.

Nesse exemplo, o melhor seria usar redirect, pois estamos trabalhando com uma requisição POST, seguindo a regra: "always redirect after post".

