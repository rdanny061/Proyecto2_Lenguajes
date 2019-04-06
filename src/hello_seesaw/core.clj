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

(defn responderEncuesta [listaEncuestas]
  (let [input  (text :columns 20)]
    (frame 
      :content 
      (vertical-panel 
        :border 5
        :items ["Digite el cuerpo de la pregunta:"
                
                (action :name "Volver"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (mainForm listaEncuestas)
                                                                 pack!
                                                                 show!))))])
      :on-close :exit)))

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
    (-> (mainForm '())
     pack!
     show!)))
