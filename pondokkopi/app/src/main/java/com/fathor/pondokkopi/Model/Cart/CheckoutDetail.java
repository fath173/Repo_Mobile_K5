package com.fathor.pondokkopi.Model.Cart;

public class CheckoutDetail {
        private Integer id_variasi;
        private Integer jumlah;
        private Integer subtotal;

        public Integer getId_variasi() {
            return id_variasi;
        }

        public void setId_variasi(Integer id_variasi) {
            this.id_variasi = id_variasi;
        }

        public Integer getJumlah() {
            return jumlah;
        }

        public void setJumlah(Integer jumlah) {
            this.jumlah = jumlah;
        }

        public Integer getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(Integer subtotal) {
            this.subtotal = subtotal;
        }

}
