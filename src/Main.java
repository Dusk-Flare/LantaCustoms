import lanta.math.LinearEquations;
import lanta.math.Matrix;

import java.util.Arrays;
import java.util.Scanner;

import lanta.dataTypes.Queue;
import lanta.utils.MenuConstructor;

public class Main {
    public static void main(String[] args) {
         record Cliente(String nome, int senha){
             @Override
             public String toString() {
                 return nome + ':' + " Senha = " + senha;
             }
         };
        Queue<Cliente> clients = new Queue<>();
        Scanner scanner = new Scanner(System.in);
        MenuConstructor menu = new MenuConstructor("Sair", "Adicionar Cliente", "Atender Cliente", "Proximo Cliente", "Mostrar Fila");
        int senhas = 0;
        while (true){
            switch (menu.getOption(scanner)) {
                case 0:
                    return;
                case 1:
                    senhas++;
                    System.out.println("Digite nome do cliente");
                    clients.push(new Cliente(scanner.nextLine(), senhas));
                    break;
                case 2:
                    System.out.println("Atendendo: "+clients.poll());
                    break;
                case 3:
                    System.out.println(clients.peek());
                    break;
                case 4:
                    System.out.println(clients);
                    break;
            }
        }
    }
}