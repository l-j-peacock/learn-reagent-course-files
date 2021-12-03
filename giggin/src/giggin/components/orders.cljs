(ns giggin.components.orders
  (:require [giggin.state :as state]))

(defn total
  []
  ;; This is the Thread Last function, and will provide the provided collection as the last argument to the
  ;; proceeding function, and then the result of that will be the last arguement of the next function, etc...
  ;; The opposite of this is Thread First (-> collection).
  (->> @state/orders
       (map (fn [[id quantity]] (* quantity (get-in @state/gigs [id :price]))))
       (reduce +)))

(defn orders
  []
  [:aside
   [:div.order
    [:div.body
     (for [[id quantity] @state/orders]
       [:div.item {:key id}
        [:div.img
         [:img {:src (get-in @state/gigs [id :img])
                :alt (get-in @state/gigs [id :title])}]]
        [:div.content
         [:p.title (str (get-in @state/gigs [id :title]) " \u00D7 " quantity)]]
        [:div.action
         [:div.price (* (get-in @state/gigs [id :price]) quantity)]
         [:button.btn.btn--link.tooltip
          {:data-tooltip "Remove"
           :on-click (fn [] (swap! state/orders dissoc id))}
          [:i.icon.icon--cross]]]])]
    [:div.total
     [:hr]
     [:div.item
      [:div.content "Total"]
      [:div.action
       [:div.price (total)]]
      [:button.btn.btn--link.tooltip
       {:data-tooltip "Remove all"
        :on-click (fn [] (reset! state/orders {}))}
       [:i.icon.icon--delete]]]]]])