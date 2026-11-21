📌 Spring Cloud Config - Centralizando Configurações (Java 21+)

Em um exemplo anterior apresentei boas práticas com @ConfigurationProperties no Spring Boot (Java 21+), neste, abordo um pouco sobre Spring Cloud Config


🚀 O que é o Spring Cloud Config?

O Spring Cloud Config fornece um servidor central de configuração, geralmente integrado a um repositório Git, permitindo que aplicações cliente carreguem suas configurações de forma remota, padronizada e segura.


✅ Benefícios:

- Centralização das configurações
- Separação entre código e configuração
- Suporte a múltiplos ambientes (dev, test, prod)
- Suporte à criptografia de propriedades sensíveis ({cipher})

🚀 Vamos pra prática?

📁 Crie um repo (Azure, Gitlab, GitHub, etc) só para os arquivos properties (ou yml) das aplicações com a seguinte estrutura de pastas:

├── properties/
│   └── NOME_DA_APLICACAO
│         └── PROFILE

Onde NOME_DA_APLICACAO deve ser IGUAL ao que está no properties da aplicação em spring.application.name. E PROFILE deve ser o profile do properties de acordo com o ambiente: dev, hml (staging) ou prod

Para este exemplo ficará assim:

├── properties/
│   └── cloudconfigpropclient
│         └── development
│         └── staging
│         └── production

🛠️ Copie o properties da aplicação para dentro da pasta de acordo com os valores para cada profile (ambiente):

├── properties/
│   └── cloudconfigpropclient
│         └── development
│               └── application.properties
│         └── staging
│         └── production

⚙️ Execute o exemplo (projeto Exemplo-Cloud-Config-Server) do Cloud Config Server localmente, e faça o teste no browser (ou Postman):

http://localhost:8888/cloudconfigpropserver/config/cloudconfigpropclient/development

🧠 Deve retornar os valores do properties que adiciononou na pasta

🔑 Antes de executar o Client, defina a variável de ambiente KEYSTORE_SECRET com a senha do certificado (pfx) da pasta /certs. O Client usa esse certificado para descriptografar os valores com o prefixo {cipher}.

A senha não fica no application.properties, que só lê a variável de ambiente:

```properties
encrypt.keyStore.password=${KEYSTORE_SECRET}
encrypt.keyStore.secret=${KEYSTORE_SECRET}
```

Sem a variável, a aplicação não sobe. Isso é proposital: é melhor falhar no início do que subir com uma senha padrão esquecida no código. Nesse caso o erro aparece como "Cannot load keys from store ... keystore password was incorrect", porque o texto ${KEYSTORE_SECRET} acaba sendo usado como senha.

⚠️ O certificado de development deste repositório é só para demonstração, e a senha dele é 57KCb1TvKq3zJ5P. Ela está aqui apenas para o exemplo rodar sem passos extras. Em um projeto real, a senha fica somente no cofre de segredos do ambiente (variável de ambiente, Secret do Kubernetes/OpenShift, Vault etc.), nunca no Git.

PowerShell:

```powershell
$env:KEYSTORE_SECRET="57KCb1TvKq3zJ5P"
mvn spring-boot:run
```

Bash:

```bash
KEYSTORE_SECRET=57KCb1TvKq3zJ5P mvn spring-boot:run
```

Na IDE, adicione KEYSTORE_SECRET nas variáveis de ambiente da configuração de execução.

⚙️ Execute o exemplo (projeto Exemplo-Cloud-Config-Client) do Cloud Config Client localmente, e faça o teste no browser (ou Postman):

http://localhost:8080/api/config

🧠 Dever retornar o response do controler com os valores lidos no properties

🧵 Conclusão

Spring Cloud Config é uma ferramenta poderosa para centralizar, versionar e proteger configurações em ambientes distribuídos. Com suporte a criptografia, você consegue manter dados sensíveis seguros mesmo com repositórios públicos.

Para projetos GitOps ou ambientes mais avançados, considere:

- Sealed Secrets
- External Secrets Operator
- Spring Cloud Vault