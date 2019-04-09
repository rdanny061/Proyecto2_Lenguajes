(ns hello-seesaw.core
  (:use seesaw.core)
  (:require [seesaw.bind :as bind]))
(use '[clojure.string :only (split triml)])

(defn mainForm [])
(defn tipoPregunta [])
(defn estadistica [])
(defn responderEncuesta [])
(defn crearEncuesta [])
(defn crearPreguntaEscala [])
(defn crearPreguntaUnica [])
(defn validacionTipo[])
(defn preguntaTipo1 [])
(defn preguntaTipo2 [])
(defn preguntaTipo2Frame [])

(defn crearPreguntaUnica [nombreEncuesta listaEncuestas listaPreguntas]
  (let [inputCuerpoPregunta  (text :columns 20)
        valueInputCuerpoPregunta  (atom "")
        
        inputOpcion  (text :columns 20)
        valueInputOpcion  (atom "")]
    
    (bind/bind inputCuerpoPregunta valueInputCuerpoPregunta)
    (bind/bind inputOpcion valueInputOpcion)
    
    (frame
      :title "Pregunta Seleccion Única"
      :content 
      (vertical-panel 
        :border 100
        :items [
                "Digite el cuerpo de la pregunta:"
                
                inputCuerpoPregunta
                
                "Agregar Opciones separadas por -"
                
                inputOpcion

                (action :name "Agregar Pregunta"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (tipoPregunta nombreEncuesta listaEncuestas (cons (cons @valueInputCuerpoPregunta (reverse (cons "tipo1" (reverse(list (split (str @valueInputOpcion)#"-")))))) listaPreguntas))
                                                                 pack!
                                                                 show!))))]) 
      
      :on-close :exit)))

(defn crearPreguntaEscala [nombreEncuesta listaEncuestas listaPreguntas]
  (let [inputCuerpoPregunta  (text :columns 20)
        valueInputCuerpoPregunta  (atom "")
        
        inputOpcion  (text :columns 20)
        valueInputOpcion  (atom "")]
    
    (bind/bind inputCuerpoPregunta valueInputCuerpoPregunta)
    (bind/bind inputOpcion valueInputOpcion)
    
    (frame
      :title "Pregunta Escala"
      :content 
      (vertical-panel 
        :border 100
        :items [
                "Digite el cuerpo de la pregunta:"
                
                inputCuerpoPregunta

                "Agregar Items separadas por -"
                
                inputOpcion

                (action :name "Agregar Pregunta"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (tipoPregunta nombreEncuesta listaEncuestas (cons (cons @valueInputCuerpoPregunta (reverse (cons "tipo2" (reverse(list (split (str @valueInputOpcion)#"-")))))) listaPreguntas))
                                                                 pack!
                                                                 show!))))]) 
      
      :on-close :exit)))


(defn crearEncuesta [listaEncuestas]
  (let [inputNombreEncuesta  (text :columns 20)
        valueNombreEncuesta  (atom "")]
        
    (bind/bind inputNombreEncuesta valueNombreEncuesta)
    
    (text! inputNombreEncuesta "Nombre de la encuesta")
    
    (frame
      :title "Crear Encuesta"
      :content 
      (vertical-panel 
        :border 100
        :items [
                "Digite el nombre de la encuesta:"
                
                inputNombreEncuesta
                
                (action :name "Crear Preguntas"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (tipoPregunta @valueNombreEncuesta listaEncuestas '())
                                                                 pack!
                                                                 show!))))
                
                (action :name "Volver"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (mainForm listaEncuestas)
                                                                 pack!
                                                                 show!))))])
                
      :on-close :exit)))

(defn preguntaTipo1 [listaEncuestas listaPreguntas1 listaPreguntas2 listaRespuestas contador]
  (let [input  (text :columns 20)
        combo (combobox :model (first(rest (first listaPreguntas2))))]
    (frame 
      :content 
      (vertical-panel 
        :border 5
        :items [
                "Tipo1"
                (str (first (first listaPreguntas2)))
                
                combo

                (action :name "Siguiente"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (validacionTipo listaEncuestas listaPreguntas1 (rest listaPreguntas2) (cons (reverse (cons "tipo1" (reverse (cons (first (first listaPreguntas2)) (list (selection combo)))))) listaRespuestas) contador)
                                                                 pack!
                                                                 show!))))])
      
      :on-close :exit)))

(defn preguntaTipo2Frame [listaEncuestas listaPreguntas1 listaPreguntas2 listaRespuestas contador listaItems listaRespuestasItems]
  (let [input  (text :columns 20)
        combo (combobox :model ["Muy bien" "Bien" "Regular" "Malo" "Muy malo"])]
    (frame 
      :content 
      (vertical-panel 
        :border 5
        :items ["Tipo2"
                (str (first (first listaPreguntas2)))
                (str (first listaItems))
                combo
                (action :name "Siguiente"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (preguntaTipo2 listaEncuestas listaPreguntas1 listaPreguntas2 listaRespuestas contador (rest listaItems) (cons (cons (first listaItems) (list (selection combo))) listaRespuestasItems))
                                                                 pack!
                                                                 show!))))])
      :on-close :exit)))

(defn preguntaTipo2 [listaEncuestas listaPreguntas1 listaPreguntas2 listaRespuestas contador listaItems listaRespuestasItems]
  (cond
    (not(empty? listaItems)) (invoke-later
                               (-> (preguntaTipo2Frame listaEncuestas listaPreguntas1 listaPreguntas2 listaRespuestas contador listaItems listaRespuestasItems)
                                   pack!
                                   show!)) 
    (empty? listaItems) (invoke-later
                          (-> (validacionTipo listaEncuestas listaPreguntas1 (rest listaPreguntas2) (cons (reverse (cons "tipo2" (reverse(cons (first (first listaPreguntas2)) (list listaRespuestasItems))))) listaRespuestas) contador)
                              pack!
                              show!))))

(defn responderEncuesta [listaEncuestas]
  (let [inputContador  (text :columns 20)
        valueContador  (atom "")
        combo (combobox :model (map #(first %) listaEncuestas))
        valueCombo (atom "")] 
    
    (bind/bind inputContador valueContador)
    
    (text! inputContador "1")
    
    (frame
      :title "Responder Encuestas"
      :content 
      (vertical-panel
        :border 100
        :items [
                "Encuestas:"
                
                combo
                
                "Cuantas veces desea contestar la encuesta"
                
                inputContador
                
                (action :name "Seleccionar"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (validacionTipo listaEncuestas (first(rest(first(filter #(= (first %) (selection combo)) listaEncuestas)))) (first(rest(first(filter #(= (first %) (selection combo)) listaEncuestas)))) '() (Integer. (str @valueContador)))
                                                                 
                                                                 pack!
                                                                 show!))))
                
                (action :name "Volver"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (mainForm listaEncuestas)
                                                                 pack!
                                                                 show!))))]))))

(defn validacionTipo [listaEncuestas listaPreguntas1 listaPreguntas2 listaRespuestas contador]

  (cond
    (= contador 0) (invoke-later
                     (-> (estadistica listaEncuestas listaRespuestas)
                         pack!
                         show!))
    (not(empty? listaPreguntas2)) (cond
                                    (= (str (first (reverse (first listaPreguntas2)))) "tipo1") (invoke-later
                                                                                                  (-> (preguntaTipo1 listaEncuestas listaPreguntas1 listaPreguntas2 listaRespuestas contador) ;lista 2 es la que va a cambiar
                                                                                                      pack!
                                                                                                      show!))
                                    (= (str (first (reverse (first listaPreguntas2)))) "tipo2") (invoke-later
                                                                                                  (-> (preguntaTipo2 listaEncuestas listaPreguntas1 listaPreguntas2 listaRespuestas contador (first(rest (first listaPreguntas2))) '()) ;lista 2 es la que va a cambiar
                                                                                                      pack!
                                                                                                      show!)))
    (empty? listaPreguntas2) (invoke-later
                               (-> (validacionTipo listaEncuestas listaPreguntas1 listaPreguntas1 listaRespuestas (- contador 1))
                                   pack!
                                   show!))))
    
    
    ;se necesita que la preguntaTipo# llame con rest

(defn estadistica [listaEncuestas listaRespuestas]
  (let [input  (text :columns 20)]
    (frame 
      :content 
      (vertical-panel 
        :border 5
        :items ["Digite el cuerpo de la pregunta:"
                
                (action :name "Volver"
                 :handler (fn [e] (println listaRespuestas)))])
      :on-close :exit)))

(defn tipoPregunta [nombreEncuesta listaEncuestas listaPreguntas]
  (let []
    (frame
      :title "Tipos de preguntas"
      :content 
      (vertical-panel 
        :border 100
        :items [
                "Seleccione el tipo de pregunta:"

                (action :name "Seleccion Unica"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (crearPreguntaUnica nombreEncuesta listaEncuestas listaPreguntas)
                                                                 pack!
                                                                 show!))))
                (action :name "Escala"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (crearPreguntaEscala nombreEncuesta listaEncuestas listaPreguntas)
                                                                 pack!
                                                                 show!))))
                (action :name "Imprimir Lista de Preguntas"
                  :handler (fn [e] (println "lista de preguntas: " listaPreguntas)))
                
                "Precione lista para terminar de crear preguntas..."
                
                (action :name "Listo"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (mainForm (cons (cons nombreEncuesta (list listaPreguntas)) listaEncuestas))
                                                                 pack!
                                                                 show!))))])
      :on-close :exit)))

(defn mainForm [listaEncuestas]
  (let []
    (frame
      :title "Ventana Principal"
      :content 
      (vertical-panel 
        :border 100
        :items [
                "¿Qué desea realizar?"
                
                (action :name "Crear Encuestas"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (crearEncuesta listaEncuestas)
                                                                 pack!
                                                                 show!))))
                
                (action :name "Responder Encuestas"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (responderEncuesta listaEncuestas)
                                                                 pack!
                                                                 show!))))
                
                (action :name "Imprimir Lista de Encuestas"
                  :handler (fn [e] (println "lista de encuestas: " listaEncuestas)))])

      :on-close :exit)))

(defn -main [& args]
  
  (invoke-later
    (-> (mainForm '(("enc3" (("pregunta1" ("si" "no") "tipo2") ("pregunta2" ("si" "no") "tipo1") ("pregunta3" ("si" "no" "tal vez") "tipo1"))) ("enc2" (("pregunta2" ("si" "no") "tipo1"))) ("enc1" ("y yo"))))
     pack!
     show!)))
