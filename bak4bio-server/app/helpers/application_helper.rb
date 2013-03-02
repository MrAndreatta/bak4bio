module ApplicationHelper
  
  def current_entity_errors(controller_name)
    if controller_name == :contents.to_s
      return @content ? @content.errors.full_messages : []
    end
    
    return []
  end
end
