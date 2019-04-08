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

(defn crearPreguntaUnica [nombreEncuesta listaEncuestas listaPreguntas]
  (let [inputCuerpoPregunta  (text :columns 20)
        valueInputCuerpoPregunta  (atom "")
        
        inputOpcion  (text :columns 20)
        valueInputOpcion  (atom "")]
    
    (bind/bind inputCuerpoPregunta valueInputCuerpoPregunta)
    (bind/bind inputOpcion valueInputOpcion)
    
    (frame 
      :content 
      (vertical-panel 
        :border 5
        :items ["Digite el cuerpo de la pregunta:"
                inputCuerpoPregunta
                
                :separator
                
                "Agregar Opciones separadas por -"
                inputOpcion
                
                ;(action :name "Agregar Opcion")
                  ;:handler (fn [e] (cons @valueInputOpcion lista)))
                
                :separator
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
      :content 
      (vertical-panel 
        :border 5
        :items ["Digite el cuerpo de la pregunta:"
                inputCuerpoPregunta
                
                :separator
                
                "Agregar Items separadas por -"
                inputOpcion
                
                ;(action :name "Agregar Opcion"
                  ;:handler (fn [e] (cons @valueInputOpcion lista))
                
                :separator
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
    
    (frame 
      :content 
      (vertical-panel 
        :border 5
        :items ["Digite el nombre de la encuesta:"
                inputNombreEncuesta
                
                (action :name "Crear Preguntas"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (tipoPregunta @valueNombreEncuesta listaEncuestas '())
                                                                 pack!
                                                                 show!))))
                (action :name "Volver"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (mainForm)
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
        :items ["Tipo1"
                (str (first (first listaPreguntas2)))
                combo
                
                
                (action :name "Siguiente"
                 :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                            (-> (validacionTipo listaEncuestas listaPreguntas1 (rest listaPreguntas2) (cons (first (first listaPreguntas2)) (list (selection combo))) contador)
                                                                pack!
                                                                show!))))])
      :on-close :exit)))
(defn preguntaTipo2 [listaEncuestas listaPreguntas1 listaPreguntas2 listaRespuestas contador]
  (let [input  (text :columns 20)]
    (frame 
      :content 
      (vertical-panel 
        :border 5
        :items ["Tipo2"
                
                (action :name "Volver"
                 :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                            (-> (mainForm)
                                                                pack!
                                                                show!))))])
      :on-close :exit)))

(defn responderEncuesta [listaEncuestas]
  (let [inputContador  (text :columns 20)
        valueContador  (atom "")
        combo (combobox :model (map #(first %) listaEncuestas))
        valueCombo (atom "")]        
    (bind/bind inputContador valueContador)
    (frame 
      :content 
   
      (vertical-panel 
        :items [
                combo
                "Cuantas veces desea contestar la encuesta"
                inputContador
                (action :name "Imprimir valor combo"
                  :handler (fn [e] (println "Valor combo: " (selection combo) "Cantidad veces: " @valueContador "nombre encuesta: " (first(first(filter #(= (first %) (selection combo)) listaEncuestas))) "lista preguntas: " (first(rest(first(filter #(= (first %) (selection combo)) listaEncuestas)))))))
                (action :name "Seleccionar"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                            (-> (validacionTipo listaEncuestas (first(rest(first(filter #(= (first %) (selection combo)) listaEncuestas)))) (first(rest(first(filter #(= (first %) (selection combo)) listaEncuestas)))) '() (Integer. (str @valueContador)))
                                                                
                                                                pack!
                                                                show!))))]))))

(defn validacionTipo [listaEncuestas listaPreguntas1 listaPreguntas2 listaRespuestas contador]

  (cond
    (= contador 0) (invoke-later
                     (-> (estadistica)
                         pack!
                         show!))
    (not(empty? listaPreguntas2)) (cond
                                    (= (str (first (reverse (first listaPreguntas2)))) "tipo1") (invoke-later
                                                                                                  (-> (preguntaTipo1 listaEncuestas listaPreguntas1 listaPreguntas2 listaRespuestas contador) ;lista 2 es la que va a cambiar
                                                                                                      pack!
                                                                                                      show!))
                                    (= (str (first (reverse (first listaPreguntas2)))) "tipo2") (invoke-later
                                                                                                  (-> (preguntaTipo2 listaEncuestas listaPreguntas1 listaPreguntas2 listaRespuestas contador) ;lista 2 es la que va a cambiar
                                                                                                      pack!
                                                                                                      show!)))
    (empty? listaPreguntas2) (invoke-later
                               (-> (validacionTipo listaEncuestas listaPreguntas1 listaPreguntas1 listaRespuestas (- contador 1))
                                   pack!
                                   show!))))
    
    
    ;se necesita que la preguntaTipo# llame con rest
    
    
    
    
  
(defn estadistica []
  (let [input  (text :columns 20)]
    (frame 
      :content 
      (vertical-panel 
        :border 5
        :items ["Digite el cuerpo de la pregunta:"
                
                (action :name "Volver"
                 :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                            (-> (mainForm)
                                                                pack!
                                                                show!))))])
      :on-close :exit)))

(defn tipoPregunta [nombreEncuesta listaEncuestas listaPreguntas]
  (let []
    (frame 
      :content 
      (vertical-panel 
        :border 20
        :items ["Seleccione el tipo de pregunta:"
                
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
                (action :name "Imprimir"
                  :handler (fn [e] (println "lista: " listaPreguntas)))
                (action :name "Listo"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (mainForm (cons (cons nombreEncuesta (list listaPreguntas)) listaEncuestas))
                                                                 pack!
                                                                 show!))))])
      :on-close :exit)))

(defn mainForm [listaEncuestas]
  (let []
    (frame 
      :content 
      (vertical-panel 
        :border 5
        :items ["Que desea hacer:" 
                
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
                (action :name "Imprimir"
                  :handler (fn [e] (println "lista: " listaEncuestas)))])
                ;(action :name "Estadisticas"
                 ; :handler (fn [e] (dispose! (all-frames)) (invoke-later
                  ;                                           (-> (estadistica)
                   ;                                              pack!
                    ;                                             show!))))])
      :on-close :exit)))


(defn -main [& args]
  
  (invoke-later
    (-> (mainForm '(("enc3" (("pregunta1" ("si" "no") "tipo1") ("pregunta2" ("si" "no") "tipo1") ("pregunta3" ("si" "no") "tipo1"))) ("enc2" (("pregunta2" ("si" "no") "tipo1"))) ("enc1" ("y yo"))))
     pack!
     show!)))
