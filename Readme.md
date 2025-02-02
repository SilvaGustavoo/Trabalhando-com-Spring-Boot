## **Trabalhando com SpringBoot e PostgreSQL** 📂📦

Foram desenvolvidos diversos projetos **SpringBoot** ao longo do aprendizado, neles foram apresentados os conceitos e usos de ``Component``, ``Beans``, ``Sigleton`` e ``Prototype`` (Scope), ``Value``, ``Configuration Properties``. Já no **Postgre** foi apresentado outros conhecimentos como a interface ``JpaRepository`` e seu uso para a adção de dados no postgree. Foi aprendido também o uso da ``@Query``e ``QueryMethods``, para manipulação e pesquisa dos dados inseridos nas tabelas.

Com bse nisso vou descrever todo meu aprendizado em relação a esse estudo.

> ***IMPORTANTE:*** ao final de cada tópico sera adicionado um link referenciando a pasta ao qual o código esta inserido, basta apenas executa-los.


### Component e Beans 🔗🫘

- ### Component

Esse foi o mais dificil de compreender, por conta de ser o primeiro e o essencial no Spring. Em resumo os ``Components`` servem para evitar a instanciação de objetos usando o ``new``. Isso ocorre pois, o próprio Spring gerencia os objetos injetando automaticamente em outras partes do código.

Com isso, os Objetos que necessitam ser instanciados é adicionado o ``@Autowired`` e na classe o ``@Component``. Isso facilita a manutenção e reduz o acoplamento do código.

``` java
@Component
public class SistemaDeMensagem {

    @Autowired
    Remetente comercial; // instanciado automaticamente

    [...]
}
```
<br>

- ### Beans

Beans tem basicamente a mesma utilidade que o Componenet é utilizado para evitar o uso do inicializador dos objetos com o `@Autowired` (new Object()), só que o beans é oara referenciar classes do Sistema, aqueles que não podem ser alterados por nós. Ex (`Gson()`, `Date()`, etc)

``` java
public class Beans {
    @Bean
    public Gson Gson() {
        return new Gson();
    }
}
```

Bean também pode ser utilizado como iniciador padrão, para classes e argumentos que ao inicializar ja possuiram determinados valores.

``` java
    @Bean
    public Remetente remetente() {
        System.out.println("CRIANDO OBJETO REMETENTE");

        Remetente remetente = new Remetente("comercial@Dio.com.br", "Comercial da DIO");

        return remetente;
    }
```

Caso não seja realizado o uso do `Scope("prototype")`, o Beans utilizará os dados não só como padrão, mas também aplicará o mesmo objeto a diversos objetos, ou seja, se existirem dois objetos criado com nomes diferentes, qualquer alteração realizada, será aplicada a ambos.

``` java
// Use como exemplo também o Bean criado acima Remetente()

// criado dois remetentes diferentes
@Autowired
Remetente comercial;

@Autowired
Remetente RH;

// na execução do programa, ambos recebem o valor padrão criado no Beans

System.out.println(comercial);
RH.setNome("Mensagem");
RH.setEmail("rh@dio.com.br");
System.out.println(RH);

// Mesmo alterando os dados apenas do 'RH',o valor será também atribuido ao 'comercial', pois ambos compartilham o MESMO OBJETO


```

> *Acesse a pasta clicando **[aqui](https://github.com/SilvaGustavoo/Trabalhando-com-Spring-Boot/tree/main/component-e-beans/primeiros-passos)*** 

<br>

### Value 🗝️📬


Value é geralmente utilizado para atribuir valores que estão escritos no documento de configuração do Spring o ``application.properties``. Com ela, podemos coletar os atributos e atribui-los diretamente na classe.

- no arquivo `application.properties`

``` properties
nome=SilvaGustavoo
email=gustavosantos728@yahoo.com.br
telefones=11658740425,11848485154,11956666666
```

essas informações são atribuidas inserindo a tag ``@Value`` como mostrado abaixo:

- no arquivo `SistemaMensagens.java`
``` java
@Value("${nome:No Found}")
private String nome;

@Value("${email}")
private String email;

@Value("${telefones}")
private List<Long> telefones;
```
> *Acesse a pasta clicando **[aqui](https://github.com/SilvaGustavoo/Trabalhando-com-Spring-Boot/tree/main/spring-propriety-value)***

<br>

## Configuration Properties ⚙️

O **Configuration Properties** tem praticamente a mesma função do ``@Value``, porém ele referencia os atributos do documento de configuração Spring utilizando prefixos. Ou seja, caso a chave desejada seja muito extenso, ele acaba deixando o código mais limpo.

- no arquivo `application.properties`

``` properties
remetente.nome=SilvaGustavoo
remetente.email=gustavosantos728@yahoo.com.br
remetente.telefones=11658740425,11848485154,11956666666
```

- no arquivo `SistemaMensagens.java`
``` java
@Configuration
@ConfigurationProperties(prefix = "remetente")
public class Remetente {

    private String nome;
    private String email;
    private List<Long> telefones;

    [...]
```

assim, todos os elementos recebem seus respectivos valores.

> *Acesse a pasta clicando **[aqui](https://github.com/SilvaGustavoo/Trabalhando-com-Spring-Boot/tree/main/usando-configuration-properties)***

<br>

## [JPA Repository](https://github.com/SilvaGustavoo/Trabalhando-com-Spring-Boot/tree/main/aula-spring-boot-data-jpa) 💾

O **JPA** tem como finalidade a comunicação do Java e o Banco de dados, fazendo com que seja salvo, coletado e alterado todos os seus dados pelos arquivos .java.

Para que isso ocorra é necessário uma serie de passos e configurações relevantes no seu código. Sendo dividas em configuração do banco de dados, criação de @chaves obrigatórias e adição de depedências.

### Configuração do Banco de Dados

primeiro é necessário escolher seu software de Banco de dados, no caso eu utilizei o PostgreSQL. Abra o seu banco de dados e crie um novo Data Base e escolha seu nome.

> **OBS:** caso for utilizar usar um diferente verifique sua implementação

<img src="img/image.png">


Após criar o banco de dados, abra ou crie o seu código spring e adicionando dependencias ``Spring Data JPA`` e o ``PostgreSQL Driver`` *(selecione o banco de dados que você escolheu)*. Por fim, adicione o código no documento de configuração do Spring o `application.properties`.

``` properties
#Opcional
spring.jpa.show-sql=true

# Cria e atualiza as tabelas, caso não estejam criadas
spring.jpa.hibernate.ddl-auto=update 


#Obrigátorio de acordo com o seu banco de dados
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/[SUA_DATABASE]
spring.datasource.username=postgres
spring.datasource.password=[SUASENHA]

```

Depois disso basta criar suas tabelas usando as suas classes, e inserindo  o ``@Entity``e definindo o ``@Id`` para que o JPA reconheça no sistema. 

- Para o ``@Id``, é possivel configurar uma geração automatica de valores, de acordo com a regra de negócio, utilizando o ``@GeneratedValue`` junto com a ``@GeneratedType.[Sua escolha]``. 

- Na configurção das colunas é utilizado o ``@Column``, e dentro dele implementado as configurações desejadas, como o ``nullabel``, ``name``, entre outras.

``` java
@Entity
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ra_aluno")
    private Integer ra;

    @Column(name = "nome_aluno", nullable = false)
    private String nome;

    [...]
}
```

Agora para a integração do seu código com o banco de dados é necessário a criação de uma interface personalziada, aqui foi chamada de ``AlunoRepository``, a qual herda o `JpaRepository`. Com ele agora é possível salvar, alterar e buscar ``Alunos``, usando os respectivos metodos, ``save()`` e ``findBy..()``.

``` java

// criação do repositorio, pode deixar vazio se quiser
public interface AlunoRepository extends JpaRepository<Aluno, Integer>{ }

@Autowired
AlunoRepository repository;

// dentro do código executavel

repository.save(aluno);
repository.findByName(String name);
```

Para as buscas é possível criar diretamente na interface, usando alguns metodos, sendo ele o ``@Query`` e o ``QueryMethodo``. 

- o ``@Query`` pode ser usado para inserir códigos SQL diretamente. O formato de escrita obriga a utilização de uma variavel para receber os dados, que abaixo foi utilizado o 'u' apartir disso é desenvolvido de acordo com a sua necessidade. Se for preciso inserir uma variavel, basta defini-la após ':' e adiciona-la dentro do ``@Param``


``` java
// procurar com query
@Query("SELECT u FROM Aluno u WHERE u.classe LIKE %:texto%")
List<Aluno> contemNaClasse(@Param("texto") String texto);
```

- ja o ``QueryMethodo`` o proprio sistema do JPA oferece opções de buscas ja inseridas, como na função abaixo, que ao inserir palavras chaves como "find"(encontre), "Containing"(contendo), etc. ele ja faz a busca com base nisso. que no caso, foi a busca da classe do aluno que conti um determinado texto

``` java
// procurar contendo x valor
public List<Aluno> findByClasseContaining(String classe); 
```

Para consuultar possiveis usos do QueryMethodo é só utilizar a tabela abaixo, onde mostra todas as palavras reservadas

<img src="img/spring-data-jpa-keywords1.PNG">
