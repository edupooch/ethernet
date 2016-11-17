package sample;

public class Controller {


    public void inicia() {
        //Iniciando as estações com seu endereço unicast e multicast
        Estacao estacao1 = new Estacao("00:0A:95:9D:68:01", "01:00:5E:00:00:AB");
        Estacao estacao2 = new Estacao("00:0A:95:9D:68:02", "01:00:5E:00:00:AB");
        Estacao estacao3 = new Estacao("00:0A:95:9D:68:03", "01:00:5E:00:0E:AA");

        //Ativando a thread que fica esperando pra receber dados
        Thread threadEstacao1 = new Thread(estacao1);
        threadEstacao1.start();
        Thread threadEstacao2 = new Thread(estacao2);
        threadEstacao2.start();
        Thread threadEstacao3 = new Thread(estacao3);
        threadEstacao3.start();

        estacao1.envia("", estacao2);
        estacao2.envia("pn5yb0AnUjRHwSOYxKKTe57sk7PeyfDr75GH4HpuOzRpFl5z1fgTFxIQ0MJoy8F0EomcRYk3eA2knkC1cAsDWQPt3efDSxlPfX42iN3wqgSc1lvRpJkKZYvgrYYN54TjtUbh1fxYuOrn18gTcWApDT5GR1Usn7B9OiPCnaLNpmrfI4qf6T61wWutoRTWrE9qevjI249DWLO9IDWI0A29i5Jnhvet78kbx1hpvkOOWnseapZaLO25AzmatCZbn07PsyHAtHBO7PQMIPtONJDjNnzOIYJ2Q5CKlq7erkFlJU46ncVsRkqYtarIPCGM9VBrC4JtFvw1YGW6rET4ADp1SzGWu9trEGIss5CtgInIhSUVDgxwz3Xt7fuwTsgyZnT4yFYmesaaX1RIr02TtAy498lEFIWVH77arIMiBDDex3HcMn3WYriIUZbELwmgWjiF5IPramZMQhTmSocBDNvzAxHvQwnRUhRIDS11KfVlzCRrSE829iEcUfirYuitFMUU7wcfskUg5wXs0oJGnlViXkr1FEyexMg8n3i1RIjZGT2D7mncsf3uXfKDo46sWMpMNucbNGWMC3WgYaG4FvDisj4GLNuUmKv1KaZMhAylfj63sFRUsfnsNxoGNcemF6RwAfLjkG2oorTfEisxAQyvbOS8W8fXc0ESUlFixqwMmNMJsrw8U3OvMXlkxcuCrwqiqbVJLb8V8IV2DbIDieax9fpwDwgpVbDthZKcgjSTckfap51pKvueqf1slEN9swufWhmnBs63IofCzwlfIoygpRoJsKYNOGioFt4YFiLq94myiA3jL7jpLhJxTUIKP5rshOrg970SQrQ4k9Kf8oFxXrCZnZ31YaZcWaFyztplyJJ3CWXgFC9rFwIstv9CK3ghVLEIlmMJQvv5pyVttLX64TUK266BkHVvB9buo077lMkaN4vUfPDnwfm6EpRyk08TzDizzOm3jlprBJxUniqD6Aum0AGxRxHSWY8R7CTV",
                estacao3);
        estacao1.envia("Bom dia grupo");


    }


    public void sim() {

    }
}
