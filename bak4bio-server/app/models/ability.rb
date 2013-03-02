class Ability
  include CanCan::Ability
  
  def initialize(user)
    user ||= User.new
    
    unless user.administrator.blank?
      can :manage, :all 
    else
      can :manage, Blast, :owner_id => user.id
      can :manage, Content, :owner_id => user.id
      can :manage, User, :id => user.id
    end
    
  end
end
