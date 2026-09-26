
export const ROUTE_PAR_ROLE: Record<string, string> = {
  ADMIN: '/admin/dashboard',
  AGRICULTEUR: '/accueil',
  ACHETEUR: '/marche',
  AGENT_ETAT: '/agent-etat/tableau-de-bord',
};

export function routePourRole(role: string): string {
  return ROUTE_PAR_ROLE[role] ?? '/accueil';
}