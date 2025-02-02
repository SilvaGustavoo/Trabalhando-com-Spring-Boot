package spring.boot.aula_spring_boot_data_jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import spring.boot.aula_spring_boot_data_jpa.model.Aluno;

/**
 * Aqui é implentado uma interface, para que tenha um código mais legível e ordenado, especificando que tal interface pertence a certa Tabela, que nesse caso fi definida para a tabela Aluno
 * 
 * para isso herdou todos os artificios da classe JpaRepository, que vai auxiliar nas pesquisas e salvamento dos nossos dados.
 * 
 * O proprio sistema do JPa oferece opções de buscas ja inseridas, como na primeira função, que ao inserir palavras chaves como "find", "Containing", etc. ele ja faz a busca com base nisso. que no caso foi a busca de classe contendo um determinado texto
 * 
 * Outra opção seria usando o @Query() que você inserir diretamente a pesquisa de SQL para encontrara determinados dados. O formato de escrita obriga a utilização de uma variavel para receber os dados, que abaixo foi utilizado o 'u' apartir disso é escrito normalmente, para buscas
 * 
 */

 @Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer>{

    // procurar contendo x valor
    public List<Aluno> findByClasseContaining(String classe); 

    // procurar com Query
    @Query("SELECT u FROM Aluno u WHERE u.idade > :age")
    List<Aluno> pegarIdade(@Param("age") int age);

    // procurar com query
    @Query("SELECT u FROM Aluno u WHERE u.classe LIKE %:texto%")
    List<Aluno> contemNaClasse(@Param("texto") String texto);
}
