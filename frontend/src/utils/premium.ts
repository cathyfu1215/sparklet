import type { UserProfile } from '../services/userService'

export function hasActivePremium(user: UserProfile | null): boolean {
  if (!user || user.accountType !== 'PREMIUM' || !user.premiumExpiryDate) {
    return false
  }
  return new Date(user.premiumExpiryDate) > new Date()
}

export function getPremiumExpiryDays(user: UserProfile | null): number {
  if (!user || user.accountType !== 'PREMIUM' || !user.premiumExpiryDate) {
    return 0
  }
  const expiryDate = new Date(user.premiumExpiryDate)
  const now = new Date()
  return Math.ceil((expiryDate.getTime() - now.getTime()) / (1000 * 60 * 60 * 24))
}

export function isPremiumExpiring(user: UserProfile | null, days: number = 7): boolean {
  const remainingDays = getPremiumExpiryDays(user)
  return remainingDays > 0 && remainingDays <= days
}
