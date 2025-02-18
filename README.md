# To-Do

<h2>💻 Técnologias utilizadas:</h2>
<ul>
  <li>Java 17</li>
  <li>SDK 22</li>
  <li>Maven 3.9.9</li>
  <li>Angular 17</li>
  <li>Node v20.16.0</li>
  <li>Banco de Dados H2 (modo memória)</li>
</ul>

<h2>🚶 Primeiros Passos</h2>
<p>Certifique-se de ter todas as técnologias necessárias instaladas</p>
<ul>
  <li><a href="https://www.oracle.com/br/java/technologies/downloads/" target="_blank">Java 17</a></li>
  <li><a href="https://www.oracle.com/br/java/technologies/downloads/" target="_blank">SDK 22</a></li>
  <li><a href="https://maven.apache.org/download.cgi" target="_blank">Maven 3.9.9</a></li>
  <li><a href="https://nodejs.org/pt/download" target="_blank">Node</a></li>
  <li><a href="https://www.h2database.com/html/download-archive.html" target="_blank">H2</a></li>
  <li>
    Angular 17:
    <ul>
      <li>Após instalar o Node, abrir terminal ou cmd e digitar o comando:</li>
    </ul>
    
  ``` 
    npm install -g @angular/cli@17 
  ```
  </li>
  
</ul>

<h2>❓ Como executar o projeto:</h2>
<h5>Clonar o repositório:</h5>
<ul>
  <li>Com o terminal ou CMD aberto, vá para o desktop:</li>

  ```
    cd Desktop
  ```
  <li>Clone o repositório:</li>
  
  ```
    git clone https://github.com/GabrielSouza45/To-Do.git
  ```
  <li>Extraia o arquivo compactado na área de trabalho usando o "Extract Here" (ou "Extraia aqui", caso o seu esteja em português)</li>

  
</ul>
<h5>BackEnd</h5>
<ul>
  <li>Ainda com o Terminal aberto no Desktop (ou onde você baixou e extraiu o projeto), vá para a pasta raiz do projeto Backend:</li>
  
  ```
    cd To-Do/Back
  ```
  <li>Na raiz do projeto Backend, digite o código para iniciar a aplicação spring:</li>
    
  ```
    mvn spring-boot:run
  ```
  <li>Com o projeto rodando, pode-se verificar o êxito acesando o link: </li>
      
  ```
    http://localhost:8080/api/tarefas
  ```
  <li>Com o projeto Spring rodando, pode-se testar utilizando ferramentas como Postman para bater nos endpoints, ou utilizar o Frontend</li>
</ul>

<h5>FrontEnd</h5>
<ul>
  <li>Após instalar o Node e o Angular CLI, mantenha o Terminal do Spring aberto, e abra uma nova aba do Terminal</li>
  <li>No terminal, acesse a pasta raiz do projeto Frontend:</li>
  
  ```
    # Vá para o Desktop (ou onde você baixou e extraiu o projeto)
    cd Desktop

    # Vá para a pasta principal do projeto
    cd To-Do

    # Vá para a pasta raíz do Frontend
    cd To-Do-Front
  ```
  <li>Intale as dependencias com o código:</li>
  
  ```
    npm install
  ```

  <li>Execute o projeto com o código:</li>
  
  ```
    ng serve
  ```
  <li>Acesse o link:</li>

  ```
    http://localhost:4200/
  ```
  <li>Divirta-se 😄</li>
</ul>




