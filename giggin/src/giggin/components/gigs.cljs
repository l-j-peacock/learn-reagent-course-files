(ns giggin.components.gigs
  (:require [giggin.state :as state]))

(defn gigs
  []
  [:main
   [:div.gigs
    ;; Here are a few ways of doing the same thing, the last will be the most concise!
    (comment
      ;; Map over the provided gigs and use the values in the markup.
      (map (fn [gig]
             [:div.gig {:key (:id gig)}
              [:img.gig__artwork {:src (:img gig) :alt (:title gig)}]
              [:div.gig__body
               [:div__title
                [:div.btn.btn--primary.float--right.tooltip {:data-tooltip "Add to order"}
                 [:i.icon.icon--plus]] (:title gig)]
               [:p.gig__price (:price gig)]
               [:p.gig__desc (:desc gig)]]]) (vals @state/gigs))

      ;; List comprehension, looks a bit more concise and readable than a map as the items you're
      ;; mapping over are stated in the first line.
      (for [gig (vals @state/gigs)]
        [:div.gig {:key (:id gig)}
         [:img.gig__artwork {:src (:img gig) :alt (:title gig)}]
         [:div.gig__body
          [:div__title
           [:div.btn.btn--primary.float--right.tooltip {:data-tooltip "Add to order"}
            [:i.icon.icon--plus]] (:title gig)]
          [:p.gig__price (:price gig)]
          [:p.gig__desc (:desc gig)]]]))

    ;; List comprehension with destructuring of the elements in the state.gigs map.
    (for [{:keys [id img title price desc]} (vals @state/gigs)]
      [:div.gig {:key id}
       [:img.gig__artwork {:src img :alt title}]
       [:div.gig__body
        [:div__title
         [:div.btn.btn--primary.float--right.tooltip
          {:data-tooltip "Add to order"
           :on-click (fn [] (swap! state/orders update id inc))}
          [:i.icon.icon--plus]] title]
        [:p.gig__price price]
        [:p.gig__desc desc]]])]])