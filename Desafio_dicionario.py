def ler_disciplinas(n):
    """
    Esta função lê as informações das disciplinas a partir do usuário, incluindo nome, nota e peso.
    Ela retorna um dicionário onde as chaves são os nomes das disciplinas e os valores são
    dicionários contendo as notas e pesos das disciplinas.
    """
    disciplinas = {}  # Cria um dicionário vazio para armazenar as informações das disciplinas.
    for _ in range(n):
        while True: # Repete até que a entrada seja válida.
            try:
                nome, nota, peso = input("Entre com o nome, nota e peso da disciplina (separados por espaço): ").split()
                nota = float(nota)
                peso = float(peso)
                
                if nota < 0 or peso < 0:
                    print("Nota e peso devem ser valores não negativos. Tente novamente.")
                    continue  # Reinicia o loop se houver erro de input.

                if not nome.isalpha():
                    print("Nome da disciplina deve conter apenas caracteres. Tente novamente.")
                    continue  # Reinicia o loop se houver erro de input.

                break  # Sai do loop se as entradas forem válidas.

            except ValueError:
                print("Entrada inválida. Certifique-se de inserir informações válidas.")

        disciplinas[nome] = {'nota': nota, 'peso': peso}
    return disciplinas


    disciplinas[nome] = {'nota': nota, 'peso': peso}  # Adiciona a disciplina ao dicionário.
    return disciplinas  # Retorna o dicionário contendo as disciplinas e suas informações.



def disciplina_menor_nota(disciplinas):
    """
    Esta função encontra a disciplina com a menor nota entre as disciplinas fornecidas.
    Ela retorna o nome da disciplina com a menor nota e sua própria nota.
    """
    menor_nota = float('inf')
    disciplina_menor = None
    for nome, info in disciplinas.items():
        if info['nota'] < menor_nota:
            menor_nota = info['nota']
            disciplina_menor = nome
    return disciplina_menor, menor_nota


def disciplina_maior_nota(disciplinas):
    """
    Esta função encontra a disciplina com a maior nota entre as disciplinas fornecidas
    e retorna o nome da disciplina com a maior nota e sua própria nota.
    """
    maior_nota = float('-inf')
    disciplina_maior = None
    for nome, info in disciplinas.items():
        if info['nota'] > maior_nota:
            maior_nota = info['nota']
            disciplina_maior = nome
    return disciplina_maior, maior_nota


def media_ponderada(disciplinas):
    """
    Esta função calcula a média ponderada das notas das disciplinas utilizando os pesos fornecidos
    e retorna a média ponderada das notas.
    """
    soma_pesos = 0
    soma_notas_pesos = 0
    for info in disciplinas.values():
        soma_pesos += info['peso']
        soma_notas_pesos += info['nota'] * info['peso']
    media = soma_notas_pesos / soma_pesos
    return media


def main():
    """
    Esta é a função principal que lê o número de disciplinas e suas informações.
    E calcula a disciplina com menor nota, com maior nota e a média ponderada, além de exibir os resultados.
    """
    while True:  # Repete até que a entrada seja válida.
        try:
            n = int(input("Informe o número de disciplinas: "))
            if n <= 0:
                print("Número inválido de disciplinas.")
            else:
                break  # Se a entrada for válida, sai do loop.
        except ValueError:
            print("Entrada inválida. Certifique-se de inserir um número válido.")

    disciplinas = ler_disciplinas(n)
    
    disciplina_menor, nota_menor = disciplina_menor_nota(disciplinas)
    disciplina_maior, nota_maior = disciplina_maior_nota(disciplinas)
    media = media_ponderada(disciplinas)
    
    print(f"Disciplina com menor nota: {disciplina_menor} - Nota: {nota_menor:.1f}")
    print(f"Disciplina com maior nota: {disciplina_maior} - Nota: {nota_maior:.1f}")
    print(f"Média ponderada das notas: {media:.2f}")

if __name__ == "__main__":
    main()
