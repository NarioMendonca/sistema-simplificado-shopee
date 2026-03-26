package com.shopee.view.console.menus;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

import com.shopee.model.Usuario;
import com.shopee.services.UsuarioService;
import com.shopee.util.Logger;

public class CadastroMenu {
    private Scanner scanner = new Scanner(System.in);
    private UsuarioService usuarioService = new UsuarioService();

    public Optional<Usuario> cadastrar() {
        String tipoUsuario = dadoInput("Qual o tipo do usuario a ser cadastrado? [vendedor ou cliente]: ");
        if (!tipoUsuario.toLowerCase().equals("cliente") && !tipoUsuario.toLowerCase().equals("vendedor")) {
            System.out.println("Tipo de usuario invalido. Informe cliente ou vendedor.");
            return Optional.empty();
        }

        System.out.println("Preencha os dados a seguir para criar seu cadastro:");
        String nome = dadoInput("Nome de usuario: ");
        String email = dadoInput("Email a ser cadastrado: ");
        String senha = dadoInput("Senha: ");

        try {
            if (tipoUsuario.toLowerCase().equals("cliente")) {
                String cpf = dadoInput("CPF: ");
                String telefone = dadoInput("Telefone: ");
                OffsetDateTime dataNascimento = inputDataNascimento();
                String endereco = dadoInput("Endereco: ");

                Usuario usuario = usuarioService.criarCliente(
                    nome,
                    email,
                    senha,
                    cpf,
                    telefone,
                    dataNascimento,
                    endereco
                );
                return Optional.of(usuario);
            } else {
                String cnpj = dadoInput("CNPJ: ");
                String razaoSocial = dadoInput("Razao Social: ");
                String telefone = dadoInput("Telefone: ");
    
                Usuario usuario = usuarioService.criarVendedor(
                    nome,
                    email,
                    senha,
                    cnpj,
                    razaoSocial,
                    telefone
                );
                return Optional.of(usuario);
            }
        } catch (Exception exception) {
            Logger.getInstance().logWarn("Falha ao realizar cadastro", exception);
            System.out.println("Falha ao realizar cadastro: " + exception.getMessage());
            return Optional.empty();
        }
    }

    private OffsetDateTime inputDataNascimento() {
        while (true) {
            // localDate aceita data no padrão americano
            String dataNascimentoString = dadoInput("Data de nascimento (yyyy-MM-dd): ");
            try {
                // utiliza LocalDate para tornar o input do usuário mais simples, e não precisar inserir hora / minuto / segundo
                LocalDate data = LocalDate.parse(dataNascimentoString);

                // transforma a data em OffSetDatetime para salvar corretamente no banco
                return data.atStartOfDay().atOffset(ZoneOffset.UTC);
            } catch (DateTimeParseException parseException) {
                Logger.getInstance().logWarn("Data de nascimento invalida informada no cadastro", parseException);
                System.out.println("Data de nascimento invalida, digite novamente.");
            }
        }
    }

    public String dadoInput(String text) {
        System.out.println(text);
        return scanner.nextLine();
    }
}
