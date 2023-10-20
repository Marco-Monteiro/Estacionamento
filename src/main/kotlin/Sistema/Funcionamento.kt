package Funcionamento.Sistema

import java.util.Scanner


class Funcionamento {
    var sc = Scanner(System.`in`)
    var cliente: MutableList<Map<String, Any>> = mutableListOf()
    var condicao = true

//cadastro do estacionamento ( entrada e saída )

    fun cadastrar() {
        while (condicao) {
            println("[1] Cadastrar entrada de cliente")
            println("[2] Lista de vagas em uso")
            println("[3] Saída de cliente")
            println("[4] Sair / Voltar ao menu principal")

            var escolha: Int = sc.nextInt()

            when (escolha) {     //Cadastro de cliente
                1 -> {
                    println("======= CADASTRAR ENTRADA =======")
                    println("Insira o nome do cliente:")
                    val nomeCliente = sc.next()

                    println("Insira a placa do carro:")
                    val placaCarro = sc.next()

                    println("Digite a hora de entrada (no formato HH:mm):")
                    //converter string para inteiro
                    val horaEntrada = sc.next()
                    val horaMinutos = horaEntrada.split(":") //split impede o bug de caracteres invalidos
                    if (horaMinutos.size == 2) {
                        try {
                            val horas = horaMinutos[0].toInt()
                            val minutos = horaMinutos[1].toInt()        //converte String para Int
                            val novoCliente = mapOf(
                                "Nome" to nomeCliente,
                                "Placa" to placaCarro,
                                "HoraEntradaHoras" to horas,
                                "HoraEntradaMinutos" to minutos
                            )
                            cliente.add(novoCliente)

                            println("Registrado com sucesso.")

                        } catch (e: NumberFormatException) {
                            println("Formato de hora inválido.")
                        }
                    } else {
                        println("Formato de hora inválido. Use HH:mm.")
                    }
                }

                //Mostra os clientes que foram registrados

                2 -> {
                    println("======= VAGAS EM USO =======")

                    for ((index, cliente) in cliente.withIndex()) {
                        println("Vaga: $index")
                        println("Nome: ${cliente["Nome"]}")
                        println("Placa: ${cliente["Placa"]}")
                        if (cliente.containsKey("HoraEntradaHoras") && cliente.containsKey("HoraEntradaMinutos")) {
                            val horaEntradaHoras = cliente["HoraEntradaHoras"]
                            val horaEntradaMinutos = cliente["HoraEntradaMinutos"]
                            println("Hora de Entrada: $horaEntradaHoras:$horaEntradaMinutos")
                        } else {
                            println("Hora de Entrada não encontrada.")
                        }

                        println("=========================")
                    }
                }
                // Saída de cliente, escolha uma vaga da lista e digite o horário de saída. em seguida calcule o valor por hora
                3 -> {
                    println("======= SAÍDA DE CLIENTE =======")
                    println("Digite o número da vaga a ser removido: ")
                    val vaga = sc.nextInt()

                    if (vaga in 0 until cliente.size) {
                        val clienteRemovido = cliente.removeAt(vaga)

                        val horaEntradaHoras = clienteRemovido["HoraEntradaHoras"] as Int
                        val horaEntradaMinutos = clienteRemovido["HoraEntradaMinutos"] as Int

                        println("Digite o horário de saída (no formato HH:mm):")
                        val horarioDeSaida = sc.next()
                        val saidaMinutos = horarioDeSaida.split(":")

                        val horasEntrada = horaEntradaHoras * 60 + horaEntradaMinutos
                        val horasSaida = saidaMinutos[0].toInt() * 60 + saidaMinutos[1].toInt()

                        val diferencaMinutos = horasSaida - horasEntrada
                        val diferencaHoras = diferencaMinutos / 60

                        val taxaPorHora = 3 // 3 reais por hora
                        val valorCobrado = diferencaHoras * taxaPorHora


                        println("Nome: ${clienteRemovido["Nome"]}")
                        println("Placa: ${clienteRemovido["Placa"]}")
                        println("Hora de Entrada: ${horaEntradaHoras}:${horaEntradaMinutos}")
                        println("Horário de Saída: $horarioDeSaida")
                        println("Tempo estacionado: $diferencaHoras horas")
                        println("Valor cobrado: $valorCobrado reais")
                        println()
                        println("Cliente da vaga $vaga removido com sucesso.")
                        println()
                        println("==========================================")
                    } else {
                        println("Vaga inválida. Por favor, insira um número de vaga válido.")
                    }
                }

                4 -> {
                    break // volta pro começo
                }

                else -> {
                    println("Opção inválida.")
                }
            }
        }
        println("Seu número de vaga é : ${cliente.size - 1}")
    }
}