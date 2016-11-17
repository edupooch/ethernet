package sample;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller {


    public void inicia() {


        byte[][] quadro = Quadro.criaQuadro("0f:20", "12:a0", "H5toJQeLL2gKSiO08aTp2HKulxCYPcJyNUQHa5l3wksUXEqRU4Mj2bMBlq6cwDXF3bQpCBzPxn4SkN9vkNsz8yg9B0DJaY7cBLstrRjoN52kHSUYJ36vNcMPaT2Qw1DS6P1kloyiI6Ioa7uSkzpCuREKq56kTJlIJxZnHi5l0EhxVaA692nkt95nSWEcnZfbgwplb98fRHCyI0QI96KgJRbb7F4NsLAm5zEki3OGm8VZju8Ohkvhl4LqpWGEWCquvHejyNe87F30O54r6qsY5PwIvtzNJuyqSRTWE11HnnrAlYG7c5bYaH2BAeY7DYzSqoYur44kMye50aKr39rzzsss7R9tQwObEy9MBRZ80mqeIeHqnjRi6WX06FosN3hT4EhlUJa9r5BuXWr0G2cr18FeMeFn0YUB66p4wnXwEZqR8e0H437q5kPc4kQwyxYmI1J0E2lNP1nQ4XqaZjuxui2NIKpnFuT9Pg52jhwqG9ER8q1JZoNjmeSL8cOfWvhRS2Yo7NF3Q04Gui3AoeS0UUNEK0NNbWxRkI36eEBrtnWM0LPaBtf3X4nwRuXRvwwLMiqbQH337BeWQ9i6Dl3FEMSBPBYjGIe2v0iy35Za0wWa4DY5C1Vmu4eUg5S50tVK9AkQ3TwKUGrJASRPN88tGGKZbntDB6ntj88ueBcVt4ASwGNNhLyguRbvf9EGf5jeokqlzWtPJ3pjrhiWc4NCeSSroOYv7IZEivh5SMZMeIrNcsWOuAE4tMatag2RmXp6sX3tH70rwa2rSVZayqwu54tjL6sxyLxf1YxuoOvB2NXJ9Qm8QFtWZeNFEIVwE7hM9qzhA02ryl73itirwURaUvqxIPYcUS8BWnE2u4m74g6jo6vYRQ3FYVepR7Ur32K0xZPQAbvEqFs4gbghrZR4QQBvaLOHfIy9le7nFxIpND7JYQEfPG3JVVgv5yu8L2COV4N0qsDiNHGeSPTsJ74MbaaMJKVe4zDVQJReKEWV9ornpJyvKxLbhxlKNcEnODMtpUzDL0R88oEF0fAcWl9sgSxX0Yt2hzzVRURtwZSYnl7HfQZMm79LG9Q5XXDknBK1N1qzHU21G5fxnu5Q85GB6s4J0CxBU6HccZuVQMzoZQu3HsF819Ba4OZee907su0Nlw3NQyCXbxgfwBnAUxCTcPpXvgIOPZo3vytnzkC6vuOOpuH18YJff5olkQmEWWm1waooTKKAOShzRYb4Ap0h0qOLxTKinTbcLIBgYEZmUseRgozVZzNEv7obeUgmRMOL3L8q2kGAMYn7z8jLB4eDeGk8cUqmzemr4eW8n3e1AJMOOyMRaIWx2X9j3O9fYAmF3hfFL1Q9siWwVaou5FnbgSwvtbeBmvqNQlZGCqAPo44ofWm1gLYwwQer47tRYiGXvfCjiguS95XhAbsghaFDNorMBqsW47tFUAAybNNf5Qq4tmpAsL8RqZKu8WV2OG0FtrEta2EjSN78mBcbaKegYxFMMMLM");

        for (byte[] array :
                quadro) {
            System.out.print(Arrays.toString(array) + " ");
        }

        System.out.println("\n" + Quadro.getDescricao(quadro));
    }
}
