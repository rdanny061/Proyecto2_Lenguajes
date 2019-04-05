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

(defn crearPreguntaUnica []
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
                
                ;(action :name "Agregar Opcion"
                  ;:handler (fn [e] (cons @valueInputOpcion lista))
                
                :separator
                (action :name "Agregar Pregunta"
                  :handler (fn [e](invoke-later
                                    (-> (tipoPregunta (cons @valueInputCuerpoPregunta (reverse (cons "tipo1" (reverse(list (split (str @valueInputOpcion)#"-")))))))
                                        pack!
                                        show!))))                
                
                (action :name "Volver"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (tipoPregunta)
                                                                 pack!
                                                                 show!))))])
      
      
      :on-close :exit)))

(defn crearPreguntaEscala []
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
                  :handler (fn [e] (println "Pregunta" @valueInputCuerpoPregunta "Opciones" (cons @valueInputCuerpoPregunta (reverse (cons "tipo2" (reverse (list (split (str @valueInputOpcion)#"-")))))))))                
                
                (action :name "Volver"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (tipoPregunta)
                                                                 pack!
                                                                 show!))))])
      
      
      :on-close :exit)))


(defn crearEncuesta []
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

(defn responderEncuesta []
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

(defn tipoPregunta []
  (let [input  (text :columns 20)]
    (frame 
      :content 
      (vertical-panel 
        :border 20
        :items ["Seleccione el tipo de pregunta:"
                
                (action :name "Seleccion Unica"
                 :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                            (-> (crearPreguntaUnica)
                                                                pack!
                                                                show!))))
                (action :name "Escala"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (crearPreguntaEscala)
                                                                 pack!
                                                                 show!))))
                (action :name "Volver"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (mainForm)
                                                                 pack!
                                                                 show!))))])
      :on-close :exit)))

(defn mainForm []
  (let []
    (frame 
      :content 
      (vertical-panel 
        :border 5
        :items ["Que desea hacer:" 
                
                (action :name "Crear Preguntas"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (tipoPregunta)
                                                                 pack!
                                                                 show!))))
                (action :name "Crear Encuestas"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (crearEncuesta)
                                                                 pack!
                                                                 show!))))
                (action :name "Responder Encuestas"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (responderEncuesta)
                                                                 pack!
                                                                 show!))))
                (action :name "Estadisticas"
                  :handler (fn [e] (dispose! (all-frames)) (invoke-later
                                                             (-> (estadistica)
                                                                 pack!
                                                                 show!))))])
      :on-close :exit)))


(defn -main [& args]
  
  (invoke-later
    (-> (mainForm)
     pack!
     show!)))
