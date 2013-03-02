class Ability
  include CanCan::Ability
  
  def initialize(user)
    user ||= User.new
    
    can :manage, Blast, :owner_id => user.id
    can :manage, Content, :owner_id => user.id
    can :manage, User, :id => user.id
    
  end
  
end
