package com.example.alex.rhymebot.BotPackage;

import java.util.ArrayList;
import java.util.List;

class Adjective
{
    private List<String> allForms;

    private String nomAczSgArtM;
    private String nomAczSgNeartM;
    private String nomAczPlArtM;
    private String nomAczPlNeartM;

    private String nomAczSgArtF;
    private String nomAczSgNeartF;
    private String nomAczPlArtF;
    private String nomAczPlNeartF;

    Adjective(String line)
    {
        this.allForms = new ArrayList<>();

        String[] lineElems = line.split(" ");

        this.nomAczSgNeartM = lineElems[0];
        this.nomAczSgArtM = lineElems[1];
        this.nomAczSgNeartF = lineElems[2];
        this.nomAczSgArtF = lineElems[3];
        this.nomAczPlNeartM = lineElems[4];
        this.nomAczPlArtM = lineElems[5];
        this.nomAczPlNeartF = lineElems[6];
        this.nomAczPlArtF = lineElems[7];

        this.allForms.add(lineElems[0]);
        this.allForms.add(lineElems[1]);
        this.allForms.add(lineElems[2]);
        this.allForms.add(lineElems[3]);
        this.allForms.add(lineElems[4]);
        this.allForms.add(lineElems[5]);
        this.allForms.add(lineElems[6]);
        this.allForms.add(lineElems[7]);
    }

    Adjective(String nomAczSgNeartM, String nomAczSgArtM, String nomAczSgNeartF, String nomAczSgArtF,
              String nomAczPlNeartM, String nomAczPlArtM, String nomAczPlNeartF, String nomAczPlArtF)
    {
        this.allForms = new ArrayList<>();

        this.nomAczSgNeartM = nomAczSgNeartM;
        this.nomAczSgArtM = nomAczSgArtM;
        this.nomAczSgNeartF = nomAczSgNeartF;
        this.nomAczSgArtF = nomAczSgArtF;
        this.nomAczPlNeartM = nomAczPlNeartM;
        this.nomAczPlArtM = nomAczPlArtM;
        this.nomAczPlNeartF = nomAczPlNeartF;
        this.nomAczPlArtF = nomAczPlArtF;

        this.allForms.add(nomAczSgNeartM);
        this.allForms.add(nomAczSgArtM);
        this.allForms.add(nomAczSgNeartF);
        this.allForms.add(nomAczSgArtF);
        this.allForms.add(nomAczPlNeartM);
        this.allForms.add(nomAczPlArtM);
        this.allForms.add(nomAczPlNeartF);
        this.allForms.add(nomAczPlArtF);
    }

    // getters
    String getNomAczSgArtM() { return this.nomAczSgArtM; }
    String getNomAczSgNeartM() { return this.nomAczSgNeartM; }
    String getNomAczPlArtM() { return this.nomAczPlArtM; }
    String getNomAczPlNeartM() { return this.nomAczPlNeartM; }

    String getNomAczSgArtF() { return this.nomAczSgArtF; }
    String getNomAczSgNeartF() { return this.nomAczSgNeartF; }
    String getNomAczPlArtF() { return this.nomAczPlArtF; }
    String getNomAczPlNeartF() { return this.nomAczPlNeartF; }

    List<String> getAllForms() { return this.allForms; }
}
