#define IN_1  2           // Motor Esquerdo GPIO2(D4)
#define IN_2  0           // Motor Esquerdo GPIO0(D3)
#define IN_3  13          // Motor Direito  GPIO13(D7)
#define IN_4  15          // Motor Direito  GPIO15(D8)

#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>

String comando; // String que recebe os comandos.

const char* nome = "Carro Wifi";
ESP8266WebServer server(8080);

void setup() {

 pinMode(IN_1, OUTPUT);
 pinMode(IN_2, OUTPUT);
 pinMode(IN_3, OUTPUT);
 pinMode(IN_4, OUTPUT); 
  
 Serial.begin(115200);
  
// Habilitando o ponto de acesso

  WiFi.mode(WIFI_AP);
  WiFi.softAP(nome); // Ponto de acesso sem senha
  IPAddress ip = WiFi.softAPIP();
 
 
// Iniciando servidor WEB
   server.on ( "/", HTTP_handleRoot );
   server.onNotFound ( HTTP_handleRoot );
   server.begin(); 

// Printa IP do Modo AP na serial
  Serial.print("IP do ponto de acesso: ");
  Serial.println(ip);
}

void frente(){ 

      digitalWrite(IN_4, LOW);
      digitalWrite(IN_3, HIGH);

      digitalWrite(IN_1, LOW);
      digitalWrite(IN_2, HIGH);
  }

void re(){ 

      digitalWrite(IN_4, HIGH);
      digitalWrite(IN_3, LOW);

      digitalWrite(IN_1, HIGH);
      digitalWrite(IN_2, LOW);
  }

void direita(){ 

      digitalWrite(IN_4, HIGH);
      digitalWrite(IN_3, LOW);

      digitalWrite(IN_1, LOW);
      digitalWrite(IN_2, HIGH);
  }

void esquerda(){

      digitalWrite(IN_4, LOW);
      digitalWrite(IN_3, HIGH);

      digitalWrite(IN_1, HIGH);
      digitalWrite(IN_2, LOW);
  }

void para(){  

      digitalWrite(IN_4, LOW);
      digitalWrite(IN_3, LOW);

      digitalWrite(IN_1, LOW);
      digitalWrite(IN_2, LOW);
 }

void loop() {
      server.handleClient();
      comando = server.arg("dir");
      if (comando == "F"){
        frente();
      }else if (comando == "B"){
        re();
      }else if (comando == "L"){
        esquerda();
      }else if (comando == "R"){
        direita();
      }else if(comando == "S"){
        para();
      }
}

void HTTP_handleRoot(void) {

if( server.hasArg("dir") ){
    Serial.println(server.arg("dir"));
  }
  server.send ( 200, "text/html", "" );
  delay(1);
}
