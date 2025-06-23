import br.Inatel.DAO.ConnectionDAO;
import br.Inatel.Model.Emprestimo;
import br.Inatel.Model.Funcionario;
import br.Inatel.Model.Livro;
import br.Inatel.Model.Usuario;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try(Connection conn = ConnectionDAO.getConnection();
            Scanner sc = new Scanner(System.in)) {

            while(true) {
                System.out.println("\n1 - Adicionar");
                System.out.println("2 - Cadastrar um emprestimo");
                System.out.println("3 - Buscar por chave primaria");
                System.out.println("4 - Listar todos");
                System.out.println("5 - Deletar");
                System.out.println("0 - Sair");
                System.out.println();
                String op = sc.nextLine();

                switch(op) {
                    case "1":
                        System.out.println("\n1 - Adicionar usuario");
                        System.out.println("2 - Adicionar livro");
                        System.out.println("3 - Adicionar funcionario");
                        System.out.println("0 - voltar");
                        System.out.println();
                        String op1 = sc.nextLine();
                        switch(op1) {
                            case "1":
                                System.out.println("CPF: ");
                                String CPF = sc.nextLine();
                                System.out.println("Nome: ");
                                String nome = sc.nextLine();
                                System.out.println("Endereco: ");
                                String endereco = sc.nextLine();
                                System.out.println("Idade: ");
                                int idade = Integer.parseInt(sc.nextLine());
                                boolean sucesso = Usuario.create(conn, new Usuario(CPF, nome, endereco, idade));
                                if (sucesso) {
                                    System.out.println("Usuario adicionado com sucesso!");
                                } else {
                                    System.out.println("Erro ao adicionar usuario!");
                                }
                                break;

                            case "2":
                                System.out.println("Titulo: ");
                                String titulo = sc.nextLine();
                                System.out.println("Autor: ");
                                String autor = sc.nextLine();
                                System.out.println("Genero: ");
                                String genero = sc.nextLine();
                                System.out.println("data de publicacao(YYYY-MM-DD): ");
                                Date anoPublicacao = Date.valueOf(sc.nextLine());
                                System.out.println("Quantidade disponivel: ");
                                int quantidadeDisponivel = Integer.parseInt(sc.nextLine());
                                int IDlivro = Livro.create(conn, new Livro(0, titulo, autor, genero, anoPublicacao, quantidadeDisponivel));
                                System.out.println("Livro adicionado com ID: " + IDlivro);
                                break;

                            case "3":
                                System.out.println("Nome: ");
                                String nome1 = sc.nextLine();
                                System.out.println("Turno: ");
                                String turno = sc.nextLine();
                                System.out.println("Idade: ");
                                int idade1 = Integer.parseInt(sc.nextLine());
                                System.out.println("Cargo: ");
                                String cargo = sc.nextLine();
                                int IDcracha = Funcionario.create(conn, new Funcionario(0, nome1, turno, idade1, cargo));
                                System.out.println("Funcionario adicionado com ID: " + IDcracha);
                                break;

                            case "0":
                                break;
                        }
                        break;

                    case "2":
                        List<Usuario> usuarios = Usuario.readAll(conn);
                        List<Livro> livros = Livro.readAll(conn);
                        List<Funcionario> funcionarios = Funcionario.readAll(conn);

                        System.out.println("Qual funcionario vai realizar o emprestimo? ");
                        for (int i = 0; i < funcionarios.size(); i++) {
                            System.out.println("(" + (i + 1) + ") " + funcionarios.get(i).Nome);
                        }
                        Funcionario funcionarioEscolhido = funcionarios.get(sc.nextInt() - 1);

                        System.out.println("Qual usuario vai pegar o livro emprestado ? ");
                        for (int i = 0; i < usuarios.size(); i++) {
                            System.out.println("(" + (i + 1) + ") " + usuarios.get(i).Nome);
                        }
                        Usuario usuarioEscolhido = usuarios.get(sc.nextInt() - 1);



                        System.out.println("Qual sera o livro ? ");
                        for (int i = 0; i < livros.size(); i++) {
                            System.out.println("(" + (i + 1) + ") " + livros.get(i).Titulo);
                        }
                        Livro livroEscolhido = livros.get(sc.nextInt() - 1);
                        sc.nextLine();

                        System.out.println("Data do emprestimo(YYYY-MM-DD): ");
                        Date dataEmprestimo = Date.valueOf(sc.nextLine());
                        System.out.println("Data de retorno(YYYY-MM-DD): ");
                        Date dataRetorno = Date.valueOf(sc.nextLine());

                        int IDemprestimo = Emprestimo.create(conn, new Emprestimo(0, dataEmprestimo, dataRetorno, "aguardando retorno", usuarioEscolhido.CPF, livroEscolhido.IDlivro, funcionarioEscolhido.IDcracha));
                        System.out.println("Emprestimo adicionado com ID: " + IDemprestimo);
                        break;

                    case "3":
                        System.out.println("\n1 - Buscar usuario");
                        System.out.println("2 - Buscar Livro");
                        System.out.println("3 - Buscar funcionario");
                        System.out.println("4 - Buscar emprestimo");
                        System.out.println("0 - voltar");
                        System.out.println();
                        String op2 = sc.nextLine();
                        switch(op2) {
                            case "1":
                                System.out.println("CPF do usuario: ");
                                String CPF = sc.nextLine();
                                Usuario user = Usuario.read(conn, CPF);
                                System.out.println("CPF: " + user.CPF);
                                System.out.println("Nome: " + user.Nome);
                                System.out.println("Endereco: " + user.Endereco);
                                System.out.println("Idade: " + user.Idade);
                                break;

                            case "2":
                                System.out.println("ID do livro: ");
                                int ID_livro = sc.nextInt();
                                sc.nextLine();
                                Livro book = Livro.read(conn, ID_livro);
                                System.out.println("ID: " + book.IDlivro);
                                System.out.println("Titulo: " + book.Titulo);
                                System.out.println("Autor: " + book.Autor);
                                System.out.println("Genero: " + book.Genero);
                                System.out.println("Data de publicacao: "+ book.AnoPublicacao);
                                break;

                            case "3":
                                System.out.println("ID do funcionario: ");
                                int ID_func = sc.nextInt();
                                sc.nextLine();
                                Funcionario func = Funcionario.read(conn, ID_func);
                                System.out.println("ID: " + func.IDcracha);
                                System.out.println("Nome: " + func.Nome);
                                System.out.println("Turno: " + func.Turno);
                                System.out.println("Idade: " + func.Idade);
                                System.out.println("Cargo: " + func.Cargo);
                                break;

                            case "4":
                                System.out.println("ID do emprestimo: ");
                                int ID_emp = sc.nextInt();
                                sc.nextLine();
                                Emprestimo emp = Emprestimo.read(conn, ID_emp);
                                System.out.println("ID: " + emp.IDemprestimo);
                                System.out.println("Data de inicio: " + emp.DataInicio);
                                System.out.println("Data de final: " + emp.DataFinal);
                                System.out.println("Status: " + emp.Status);
                                System.out.println("CPF do Usuario: " + emp.Usuario_CPF);
                                System.out.println("Cracha do funcionario: " + emp.Funcionario_IDcracha);
                                System.out.println("ID do livro: " + emp.Livro_IDlivro);
                                break;

                            case "0":
                                break;


                        }
                        break;

                    case "4":
                        System.out.println("\n1 - Listar usuario");
                        System.out.println("2 - Listar livro");
                        System.out.println("3 - Listar funcionario");
                        System.out.println("4 - Listar emprestimo");
                        System.out.println("0 - voltar");
                        System.out.println();
                        String op3 = sc.nextLine();
                        switch(op3) {
                            case "1":
                                List<Usuario> usuarios1 = Usuario.readAll(conn);
                                for(Usuario usuario : usuarios1) {
                                    System.out.println("CPF: " + usuario.CPF);
                                    System.out.println("Nome: " + usuario.Nome);
                                    System.out.println("Endereco: " + usuario.Endereco);
                                    System.out.println("Idade: " + usuario.Idade);
                                    System.out.println();
                                }
                                break;

                            case "2":
                                List<Livro> livros1 = Livro.readAll(conn);
                                for(Livro livro : livros1) {
                                    System.out.println("ID: " + livro.IDlivro);
                                    System.out.println("Titulo: " + livro.Titulo);
                                    System.out.println("Autor: " + livro.Autor);
                                    System.out.println("Genero: " + livro.Genero);
                                    System.out.println("Data de publicacao: "+ livro.AnoPublicacao);
                                    System.out.println();
                                }
                                break;

                            case "3":
                                List<Funcionario> funcionarios1 = Funcionario.readAll(conn);
                                for(Funcionario funcionario : funcionarios1) {
                                    System.out.println("ID: " + funcionario.IDcracha);
                                    System.out.println("Nome: " + funcionario.Nome);
                                    System.out.println("Turno: " + funcionario.Turno);
                                    System.out.println("Idade: " + funcionario.Idade);
                                    System.out.println("Cargo: " + funcionario.Cargo);
                                    System.out.println();
                                }
                                break;

                            case "4":
                                List<Emprestimo> emprestimos1 = Emprestimo.readAll(conn);
                                for(Emprestimo emprestimo : emprestimos1) {
                                    System.out.println("ID: " + emprestimo.IDemprestimo);
                                    System.out.println("Data de inicio: " + emprestimo.DataInicio);
                                    System.out.println("Data de final: " + emprestimo.DataFinal);
                                    System.out.println("Status: " + emprestimo.Status);
                                    System.out.println("CPF do Usuario: " + emprestimo.Usuario_CPF);
                                    System.out.println("Cracha do funcionario: " + emprestimo.Funcionario_IDcracha);
                                    System.out.println("ID do livro: " + emprestimo.Livro_IDlivro);
                                    System.out.println();
                                }
                                break;

                            case "0":
                                break;
                        }
                        break;


                    case "5":
                        System.out.println("\n1 - Deletar usuario");
                        System.out.println("2 - Deletar livro");
                        System.out.println("3 - Deletar funcionario");
                        System.out.println("4 - Deletar emprestimo");
                        System.out.println("0 - voltar");
                        System.out.println();
                        String op4 = sc.nextLine();
                        switch(op4) {
                            case "1":
                                System.out.println("CPF do usuario: ");
                                String CPF = sc.nextLine();
                                Usuario.delete(conn, CPF);
                                System.out.println("Usuario de CPF " + CPF + " foi deletado");
                                break;

                            case "2":
                                System.out.println("ID do livro: ");
                                int ID_livro = sc.nextInt();
                                sc.nextLine();
                                Livro.delete(conn, ID_livro);
                                System.out.println("Livro de ID " + ID_livro + " foi deletado");
                                break;

                            case "3":
                                System.out.println("ID do funcionario: ");
                                int ID_func = sc.nextInt();
                                sc.nextLine();
                                Funcionario.delete(conn, ID_func);
                                System.out.println("Funcionario de ID " + ID_func + " foi deletado");
                                break;

                            case "4":
                                System.out.println("ID do emprestimo: ");
                                int ID_emp = sc.nextInt();
                                sc.nextLine();
                                Emprestimo.delete(conn, ID_emp);
                                System.out.println("Emprestimo de ID " + ID_emp + " foi deletado");
                                break;

                            case "0":
                                break;
                        }
                        break;


                    case "0":
                        System.exit(0);

                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}