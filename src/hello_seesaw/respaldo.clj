;; cada defn de abajo lo estoy tomando de los ejemplos de:
;; https://github.com/daveray/seesaw/tree/master/test/seesaw/test/examples
;; HAY QUE AJUSTAR LOS REQUERIMIENTOS DE ARIBA SEGÚN EL EJEMPLO

;;FUNCION PROPIA
(defn indices [n]
  (if (= n 0))
  '()
  (concat (indices (- n 1))
    (list n)))

(defn pantalla []  (frame :title "Find/Replace" :content 
                     (form-panel
                       :items [
                               [nil :fill :both :insets (java.awt.Insets. 5 5 5 5) :gridx 0 :gridy 0]
                               
                               
                               [(label :text "Find What:" :halign :right)]
                               
                               
                               [(text :columns 20) :grid :next :weightx 1.0]
                               
                               
                               [(grid-panel :columns 1)
                                  :items (map #(button :text %) ["Find" "Replace" "Replace All" "Close" "Help"])
                                  :grid :next :gridheight 5 :anchor :north :weightx 0]
                               
                               
                               [(label :text "Replace With:" :halign :right) :gridheight 1 :grid :wrap]
                               
                               
                               [(text :columns 20) :grid :next :weightx 1.0]
                               
                               
                               [[5 :by 5] :grid :wrap]
                               
                               
                               [(grid-panel :columns 2 
                                  :items (map #(checkbox :text %) 
                                           ["Match Case" "Wrap Around" 
                                            "Whole Words" "Search Solution"
                                            "Regular Expressions" "Search Backwards"
                                            "Highlight Results" "Incremental Search"]))
                                :grid :next]])
                     
                     :on-close :exit)) ;;esta propiedad para que cierre la ventana, la estoy agregando para cada ejemplo que copio
      


(defn pantalla2 []
  (let [input1  (text :columns 20)
        valueInput1  (atom "")
        output2 (text :editable? false)
        
        input2  (text :columns 20)
        valueInput2  (atom "")
        output1 (text :editable? false)]
        
        
    ; Set up our binding chain
    (bind/bind input1                         ; Take changes to input
      (bind/transform #(.toUpperCase %)) ; Pass through upper case transform
      valueInput1                         ; Put the value in the atom 
      output1)                       ; Show the final value in the output text doc
    (bind/bind input2                         ; Take changes to input
      (bind/transform #(.toUpperCase %)) ; Pass through upper case transform
      valueInput2                         ; Put the value in the atom 
      output2) 
    (text! input1 "Warner")
    (text! input2 "se la come")
    (frame 
      :content 
      (vertical-panel 
        :border 5
        :items ["Enter text here:" 
                input1
                input2
                :separator
                "Changed atom is reflected here:"
                output1
                output2
                :separator
                (action :name "Print atom value to console"
                  :handler (fn [e] (println "Current atom value is: " @valueInput2)))])
      
      :on-close :exit)))



(println "Pregunta" @valueInputCuerpoPregunta "Opciones" (cons @valueInputCuerpoPregunta (reverse (cons "tipo1" (reverse(list (split (str @valueInputOpcion)#"-")))))))


