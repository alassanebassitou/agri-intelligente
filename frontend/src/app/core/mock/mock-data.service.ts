import { Injectable } from '@angular/core';

// ⚠️ MOCK — toutes les données ici sont statiques, pour la démo de lundi.
// À remplacer par de vrais appels HTTP une fois les endpoints backend prêts
// (garder les mêmes noms de méthodes pour limiter les changements dans les composants).

export interface DashboardKpiData {
  value: number;
  label: string;
  icon: string;
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
}