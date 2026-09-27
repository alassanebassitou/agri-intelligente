import { Injectable } from '@angular/core';

// ⚠️ MOCK — toutes les données ici sont statiques, pour la démo de lundi.
// À remplacer par de vrais appels HTTP une fois les endpoints backend prêts
// (garder les mêmes noms de méthodes pour limiter les changements dans les composants).

export interface DashboardKpiData {
  value: number;
  label: string;
  icon: string;
}

export type TypeAlerte = 'climat' | 'phytosanitaire' | 'planification';
 
export interface AlerteMock {
  id: number;
  type: TypeAlerte;
  message: string;
  parcelleName: string;
  createdAt: string; // libellé relatif lisible, pas de vraie logique de date pour la démo
}

export type StatutModule = 'Terminé' | 'En cours' | 'À faire';

export interface FormationModuleMock {
  id: number;
  titre: string;
  description: string;
  dureeMinutes: number;
  categorie: string;
  icon: string;
  statut: StatutModule;
}

export interface ParcelleMock {
  id: number;
  nom: string;
  culture: string;
  superficieHa: number;
  localisation: string;
  dernierControle: string; // date lisible, pas de vraie logique de date pour la démo
}

export interface WeatherMock {
  localisation: string;
  temperatureC: number;
  condition: string;
  icon: string; // nom d'icône Material
  humidite: number; // en %
  precipitationPrevue: string;
  conseil: string;
}

export interface UserProfileMock {
  firstname: string;
  lastname: string;
}

export interface AdminKpiData {
  value: number;
  label: string;
  icon: string;
  accentColor: string;
}

export interface InscriptionMoisMock {
  mois: string;
  total: number;
}

export interface RepartitionRoleMock {
  role: string;
  count: number;
  color: string;
}

export interface UtilisateurRecentMock {
  nom: string;
  role: string;
  localisation: string;
  dateInscription: string;
  statut: 'Actif' | 'En attente';
}

@Injectable({ providedIn: 'root' })
export class MockDataService {
  getUserProfile(): UserProfileMock {
    return { firstname: 'Alassane', lastname: 'Abdou-Bassitou' };
  }

  getAdminKpis(): AdminKpiData[] {
    return [
      { value: 248, label: 'Utilisateurs inscrits', icon: 'group', accentColor: '#2d5f3e' },
      { value: 11200, label: 'Hectares cultivés', icon: 'landscape', accentColor: '#4caf7d' },
      { value: 6000, label: 'Culture de rente', icon: 'pending_actions', accentColor: '#d99a3d' },
      { value: 900, label: 'Culture vivrière', icon: 'notifications_active', accentColor: '#b5453f' },
    ];
  }

  getInscriptionsParMois(): InscriptionMoisMock[] {
    return [
      { mois: 'Avr', total: 18 },
      { mois: 'Mai', total: 27 },
      { mois: 'Juin', total: 34 },
      { mois: 'Juil', total: 41 },
      { mois: 'Août', total: 58 },
      { mois: 'Sept', total: 70 },
    ];
  }

  getRepartitionParRole(): RepartitionRoleMock[] {
    return [
      { role: 'Cotton', count: 168, color: '#2d5f3e' },
      { role: 'Soja', count: 54, color: '#4caf7d' },
      { role: "Maïs", count: 22, color: '#a9c9b3' },
      { role: 'Haricot', count: 4, color: '#d99a3d' },
    ];
  }

  getUtilisateursRecents(): UtilisateurRecentMock[] {
    return [
      { nom: 'Fifamè Houngbo', role: 'Agriculteur', localisation: 'Abomey-Calavi', dateInscription: '25/09/2026', statut: 'Actif' },
      { nom: 'Moussa Idrissou', role: 'Acheteur', localisation: 'Parakou', dateInscription: '24/09/2026', statut: 'Actif' },
      { nom: "Chabi Agent État", role: "Agent de l'État", localisation: 'Cotonou', dateInscription: '23/09/2026', statut: 'En attente' },
      { nom: 'Rachidatou Sanni', role: 'Agriculteur', localisation: 'Zè', dateInscription: '22/09/2026', statut: 'Actif' },
      { nom: 'Bio Tchoukou', role: 'Agriculteur', localisation: 'Djougou', dateInscription: '21/09/2026', statut: 'En attente' },
    ];
  }

  getDashboardKpis(): DashboardKpiData[] {
    return [
      { value: 3, label: 'Parcelles enregistrées', icon: 'landscape' },
      { value: 2, label: 'Alertes actives', icon: 'notifications_active' },
      { value: 4, label: 'Modules de formation suivis', icon: 'school' },
      { value: 1, label: 'Campagnes en cours', icon: 'agriculture' },
    ];
  }

  getParcelles(): ParcelleMock[] {
    return [
      {
        id: 1,
        nom: 'Parcelle Nord',
        culture: 'Maïs',
        superficieHa: 2.4,
        localisation: 'Abomey-Calavi',
        dernierControle: 'Il y a 2 jours',
      },
      {
        id: 2,
        nom: 'Parcelle Sud',
        culture: 'Manioc',
        superficieHa: 1.1,
        localisation: 'Abomey-Calavi',
        dernierControle: 'Il y a 5 jours',
      },
      {
        id: 3,
        nom: 'Parcelle Rivière',
        culture: 'Riz',
        superficieHa: 3.6,
        localisation: 'Zè',
        dernierControle: "Aujourd'hui",
      },
    ];
  }

  getWeather(): WeatherMock {
    return {
      localisation: 'Abomey-Calavi',
      temperatureC: 29,
      condition: 'Ensoleillé avec passages nuageux',
      icon: 'wb_sunny',
      humidite: 68,
      precipitationPrevue: 'Faible risque de pluie en fin de journée',
      conseil: 'Conditions favorables pour un traitement phytosanitaire ce matin.',
    };
  }

  getAlertes(): AlerteMock[] {
    return [
      {
        id: 1,
        type: 'climat',
        message: 'Fortes pluies prévues, ne mettez pas d\'engrais',
        parcelleName: 'Parcelle Rivière',
        createdAt: 'Il y a 3 heures',
      },
      {
        id: 2,
        type: 'phytosanitaire',
        message: 'Conditions favorables à une maladie sur votre maïs, inspectez vos feuilles',
        parcelleName: 'Parcelle Nord',
        createdAt: 'Il y a 1 jour',
      },
      {
        id: 3,
        type: 'planification',
        message: 'Votre récolte sera prête dans 12 jours',
        parcelleName: 'Parcelle Sud',
        createdAt: 'Il y a 2 jours',
      },
    ];
  }

  
  getFormationModules(): FormationModuleMock[] {
    return [
      {
        id: 1,
        titre: 'Bien utiliser les alertes climatiques',
        description: 'Comprendre les alertes météo reçues sur la plateforme et adapter vos pratiques en conséquence.',
        dureeMinutes: 10,
        categorie: 'Climat',
        icon: 'wb_sunny',
        statut: 'Terminé',
      },
      {
        id: 2,
        titre: 'Reconnaître les maladies courantes du maïs',
        description: 'Identifier les premiers signes de maladies fréquentes et savoir quand agir.',
        dureeMinutes: 15,
        categorie: 'Phytosanitaire',
        icon: 'bug_report',
        statut: 'En cours',
      },
      {
        id: 3,
        titre: 'Techniques de rotation des cultures',
        description: 'Choisir le bon enchaînement de cultures pour préserver la fertilité de vos parcelles.',
        dureeMinutes: 20,
        categorie: 'Agronomie',
        icon: 'sync',
        statut: 'À faire',
      },
      {
        id: 4,
        titre: 'Fertilisation raisonnée et calendrier d\'apport',
        description: "Adapter les quantités et le moment d'apport d'engrais selon la culture et la saison.",
        dureeMinutes: 12,
        categorie: 'Agronomie',
        icon: 'eco',
        statut: 'À faire',
      },
      {
        id: 5,
        titre: 'Irrigation économe en saison sèche',
        description: "Réduire la consommation d'eau tout en maintenant de bons rendements.",
        dureeMinutes: 18,
        categorie: 'Climat',
        icon: 'water_drop',
        statut: 'À faire',
      },
    ];
  }
}