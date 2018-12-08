package com.f4pl0.ami.Fragments.NewPostFragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.f4pl0.ami.Fragments.SetupFragments.SetupInterestsFragmentCategory;
import com.f4pl0.ami.NewPostActivity;
import com.f4pl0.ami.R;


public class NewPostCategoryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        ((NewPostActivity)getActivity()).showLoading("Loading interests...");
        final Thread thread = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                try {
                    wait(1000);
                    String[] Kunst = new String[11];
                    Kunst[0] = "Design";
                    Kunst[1] = "Mode / Fashion";
                    Kunst[2] = "Fotografie";
                    Kunst[3] = "Wohnen / Deko";
                    Kunst[4] = "Architektur";
                    Kunst[5] = "Street Art";
                    Kunst[6] = "Malerei";
                    Kunst[7] = "Theater";
                    Kunst[8] = "Tattoos / Piercing";
                    Kunst[9] = "Tanz";
                    Kunst[10] = "Gesang";

                    String[] Technologie = new String[8];
                    Technologie[0] = "Computer";
                    Technologie[1] = "Smartphones / Tablets";
                    Technologie[2] = "Software / Apps";
                    Technologie[3] = "Hardware";
                    Technologie[4] = "Wearables";
                    Technologie[5] = "Programmieren";
                    Technologie[6] = "Neue Technologien";
                    Technologie[7] = "Futurismus";

                    String[] Natur = new String[5];
                    Natur[0] = "Tiere";
                    Natur[1] = "Pflanzen";
                    Natur[2] = "Menschen";
                    Natur[3] = "Landschaften";
                    Natur[4] = "Umwelt";

                    String[] EssenUndGetranke = new String[8];
                    EssenUndGetranke[0] = "Rezepte";
                    EssenUndGetranke[1] = "Kochen";
                    EssenUndGetranke[2] = "Backen";
                    EssenUndGetranke[3] = "Grillen";
                    EssenUndGetranke[4] = "Desserts";
                    EssenUndGetranke[5] = "Säfte / Smoothies";
                    EssenUndGetranke[6] = "Alkoholische Getränke";
                    EssenUndGetranke[7] = "Vegetarisch / Vegan";

                    String[] Reisen = new String[5];
                    Reisen[0] = "Länder / Städte";
                    Reisen[1] = "Sehenswürdigkeiten";
                    Reisen[2] = "Outdoor / Campen";
                    Reisen[3] = "Wandern";
                    Reisen[4] = "Ökotourismus";

                    String[] Musik = new String[16];
                    Musik[0] = "Electro";
                    Musik[1] = "Pop";
                    Musik[2] = "Hip Hop / Rap";
                    Musik[3] = "R&B / Soul";
                    Musik[4] = "Indie";
                    Musik[5] = "Rock";
                    Musik[6] = "Reggae";
                    Musik[7] = "Klassisch";
                    Musik[8] = "Metal";
                    Musik[9] = "Blues";
                    Musik[10] = "Jazz";
                    Musik[11] = "Latino";
                    Musik[12] = "Schlager";
                    Musik[13] = "Country";
                    Musik[14] = "Gospel";
                    Musik[15] = "World";

                    String[] FilmeUndSerien = new String[14];
                    FilmeUndSerien[0] = "Action / Abenteuer";
                    FilmeUndSerien[1] = "Komödie";
                    FilmeUndSerien[2] = "Drama";
                    FilmeUndSerien[3] = "Dokus / Reportagen";
                    FilmeUndSerien[4] = "Science-Fiction";
                    FilmeUndSerien[5] = "Fantasy";
                    FilmeUndSerien[6] = "Horror / Thriller";
                    FilmeUndSerien[7] = "Independent";
                    FilmeUndSerien[8] = "Romantik";
                    FilmeUndSerien[9] = "Anime";
                    FilmeUndSerien[10] = "Western";
                    FilmeUndSerien[11] = "Sport";
                    FilmeUndSerien[12] = "Krimi";
                    FilmeUndSerien[13] = "Musik / Tanz";

                    String[] Sport = new String[29];
                    Sport[0] = "Fitness";
                    Sport[1] = "Joggen";
                    Sport[2] = "Yoga";
                    Sport[3] = "Fussball";
                    Sport[4] = "Basketball";
                    Sport[5] = "Handball";
                    Sport[6] = "Volleyball";
                    Sport[7] = "Kampfsport";
                    Sport[8] = "Klettern / Bouldern";
                    Sport[9] = "Fahrradfahren";
                    Sport[10] = "Eishockey";
                    Sport[11] = "Tennis";
                    Sport[12] = "Tischtennis";
                    Sport[13] = "Schwimmen";
                    Sport[14] = "American Football / Rugby";
                    Sport[15] = "Baseball";
                    Sport[16] = "Skateboarden";
                    Sport[17] = "Surfen";
                    Sport[18] = "Skifahren";
                    Sport[19] = "Snowboarden";
                    Sport[20] = "Billiard";
                    Sport[21] = "Dart";
                    Sport[22] = "Bowling";
                    Sport[23] = "Golf";
                    Sport[24] = "Tauchen";
                    Sport[25] = "Reiten";
                    Sport[26] = "Gymnastik";
                    Sport[27] = "Motorsport";
                    Sport[28] = "Schach";

                    String[] Videospiele = new String[12];
                    Videospiele[0] = "Abenteuer";
                    Videospiele[1] = "Beat ’em ups";
                    Videospiele[2] = "Ego-Shooter / Third-Person-Shooter";
                    Videospiele[3] = "Jump ’n’ Runs";
                    Videospiele[4] = "Sport";
                    Videospiele[5] = "Strategie";
                    Videospiele[6] = "Open-World";
                    Videospiele[7] = "Rollenspiele";
                    Videospiele[8] = "Shoot ’em ups";
                    Videospiele[9] = "Simulation";
                    Videospiele[10] = "Musik / Tanz";
                    Videospiele[11] = "Puzzle / Quiz";

                    String[] Bucher = new String[14];
                    Bucher[0] = "Abenteuer";
                    Bucher[1] = "Science-Fiction";
                    Bucher[2] = "Fantasy";
                    Bucher[3] = "Comics / Mangas";
                    Bucher[4] = "Autobiographie / Memoiren";
                    Bucher[5] = "Humor";
                    Bucher[6] = "Romantik";
                    Bucher[7] = "Krimi / Mystery";
                    Bucher[8] = "Horror / Thriller";
                    Bucher[9] = "Historisch";
                    Bucher[10] = "Sachbücher";
                    Bucher[11] = "Kochbücher";
                    Bucher[12] = "Zeitungen";
                    Bucher[13] = "Zeitschriften / Magazine";

                    String[] Wissen = ("Nachrichten\n" +
                            "Astronomie / Weltraum\n" +
                            "Psychologie\n" +
                            "Geschichte\n" +
                            "Politik\n" +
                            "Medizin\n" +
                            "Physik\n" +
                            "Informatik\n" +
                            "Philosophie\n" +
                            "Mathematik\n" +
                            "Biologie\n" +
                            "Archäologie\n" +
                            "Anthropologie\n" +
                            "Paläontologie\n" +
                            "Ingenieurwesen\n" +
                            "Geographie\n" +
                            "Chemie\n" +
                            "Ethnologie\n" +
                            "Genetik\n" +
                            "Evolutionsforschung\n" +
                            "Geowissenschaften\n" +
                            "Linguistik\n" +
                            "Rechtswissenschaften\n" +
                            "Meteorologie\n" +
                            "Neurowissenschaften\n" +
                            "Soziologie\n" +
                            "Mythologie\n" +
                            "Statistik").split("\n");

                    String[] Gesundheit = ("Ernährung\n" +
                            "Schlaf\n" +
                            "Vorsorge").split("\n");

                    String[] Unterhaltung = ("Humor\n" +
                            "Heimwerken / Basteln\n" +
                            "Promis / Klatsch\n" +
                            "Gärtnern\n" +
                            "TV / Radio\n" +
                            "Clubs / Diskos\n" +
                            "Restaurants\n" +
                            "Bars / Kneipen\n" +
                            "Beauty\n" +
                            "Shopping\n" +
                            "Luxus\n" +
                            "Gesellschaftsspiele\n" +
                            "Rätsel").split("\n");

                    String[] Wirtschaft = ("Unternehmertum / Entrepreneurship\n" +
                            "Persönliche Finanzen\n" +
                            "Startups\n" +
                            "Immobilien\n" +
                            "Führung / Management\n" +
                            "Industrie\n" +
                            "Marketing\n" +
                            "Investieren\n" +
                            "Banken\n" +
                            "Versicherungen\n" +
                            "E-Commerce\n" +
                            "Regierung / Gesetze\n" +
                            "Steuern\n" +
                            "Aktien").split("\n");

                    String[] Veranstaltungen = ("Festivals\n" +
                            "Konzerte\n" +
                            "Opern / Operetten\n" +
                            "Theater\n" +
                            "Shows\n" +
                            "Feste\n" +
                            "Sport-Events\n" +
                            "Messen").split("\n");

                    String[] Fahrzeuge = ("Automobil\n" +
                            "Motorrad\n" +
                            "Eisenbahn\n" +
                            "Schifffahrt\n" +
                            "Luftfahrt\n" +
                            "Raumfahrt").split("\n");

                    String[] Beziehungen = ("Familie\n" +
                            "Erziehung / Kinder\n" +
                            "Partnerschaft / Ehe\n" +
                            "Liebe\n" +
                            "Freundschaft").split("\n");

                    String[] Kultur = ("Weltkultur\n" +
                            "Spiritualität\n" +
                            "Religion\n" +
                            "Moral / Ethik\n" +
                            "Wohltätigkeit").split("\n");

                    String[] Selbstentwicklung = ("Karriere\n" +
                            "Bildung\n" +
                            "Organisation\n" +
                            "Produktivität\n" +
                            "Motivation").split("\n");
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                            .add(R.id.newPostCategoryLyt, new SetupInterestsFragmentCategory("Kunst", Kunst));
                    fragmentTransaction.commit();

                    fragmentTransaction = fragmentManager.beginTransaction()
                            .add(R.id.newPostCategoryLyt, new SetupInterestsFragmentCategory("Technologie", Technologie));
                    fragmentTransaction.commit();

                    fragmentTransaction = fragmentManager.beginTransaction()
                            .add(R.id.newPostCategoryLyt, new SetupInterestsFragmentCategory("Natur", Natur));
                    fragmentTransaction.commit();

                    fragmentTransaction = fragmentManager.beginTransaction()
                            .add(R.id.newPostCategoryLyt, new SetupInterestsFragmentCategory("Essen und Getränke", EssenUndGetranke));
                    fragmentTransaction.commit();

                    fragmentTransaction = fragmentManager.beginTransaction()
                            .add(R.id.newPostCategoryLyt, new SetupInterestsFragmentCategory("Reisen", Reisen));
                    fragmentTransaction.commit();

                    fragmentTransaction = fragmentManager.beginTransaction()
                            .add(R.id.newPostCategoryLyt, new SetupInterestsFragmentCategory("Musik", Musik));
                    fragmentTransaction.commit();

                    fragmentTransaction = fragmentManager.beginTransaction()
                            .add(R.id.newPostCategoryLyt, new SetupInterestsFragmentCategory("Filme und Serien", FilmeUndSerien));
                    fragmentTransaction.commit();

                    fragmentTransaction = fragmentManager.beginTransaction()
                            .add(R.id.newPostCategoryLyt, new SetupInterestsFragmentCategory("Sport", Sport));
                    fragmentTransaction.commit();

                    fragmentTransaction = fragmentManager.beginTransaction()
                            .add(R.id.newPostCategoryLyt, new SetupInterestsFragmentCategory("Videospiele / Gaming", Videospiele));
                    fragmentTransaction.commit();

                    fragmentTransaction = fragmentManager.beginTransaction()
                            .add(R.id.newPostCategoryLyt, new SetupInterestsFragmentCategory("Bücher / Literatur", Bucher));
                    fragmentTransaction.commit();

                    fragmentTransaction = fragmentManager.beginTransaction()
                            .add(R.id.newPostCategoryLyt, new SetupInterestsFragmentCategory("Wissen", Wissen));
                    fragmentTransaction.commit();

                    fragmentTransaction = fragmentManager.beginTransaction()
                            .add(R.id.newPostCategoryLyt, new SetupInterestsFragmentCategory("Gesundheit", Gesundheit));
                    fragmentTransaction.commit();

                    fragmentTransaction = fragmentManager.beginTransaction()
                            .add(R.id.newPostCategoryLyt, new SetupInterestsFragmentCategory("Unterhaltung und Freizeit", Unterhaltung));
                    fragmentTransaction.commit();

                    fragmentTransaction = fragmentManager.beginTransaction()
                            .add(R.id.newPostCategoryLyt, new SetupInterestsFragmentCategory("Wirtschaft", Wirtschaft));
                    fragmentTransaction.commit();

                    fragmentTransaction = fragmentManager.beginTransaction()
                            .add(R.id.newPostCategoryLyt, new SetupInterestsFragmentCategory("Veranstaltungen", Veranstaltungen));
                    fragmentTransaction.commit();

                    fragmentTransaction = fragmentManager.beginTransaction()
                            .add(R.id.newPostCategoryLyt, new SetupInterestsFragmentCategory("Fahrzeuge / Transport", Fahrzeuge));
                    fragmentTransaction.commit();

                    fragmentTransaction = fragmentManager.beginTransaction()
                            .add(R.id.newPostCategoryLyt, new SetupInterestsFragmentCategory("Beziehungen", Beziehungen));
                    fragmentTransaction.commit();

                    fragmentTransaction = fragmentManager.beginTransaction()
                            .add(R.id.newPostCategoryLyt, new SetupInterestsFragmentCategory("Kultur und Gesellschaft", Kultur));
                    fragmentTransaction.commit();

                    fragmentTransaction = fragmentManager.beginTransaction()
                            .add(R.id.newPostCategoryLyt, new SetupInterestsFragmentCategory("Selbstentwicklung", Selbstentwicklung));
                    fragmentTransaction.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ((NewPostActivity)getActivity()).dismissLoading();
            }
        });
        thread.start();

        return inflater.inflate(R.layout.fragment_new_post_category, container, false);
    }
}
